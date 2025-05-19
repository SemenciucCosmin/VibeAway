package com.example.vibeaway.feature.onboarding.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.vibeaway.data.database.repository.DatabaseRepository
import com.example.vibeaway.data.quiz.datasource.BFIQuestionsDataSource
import com.example.vibeaway.feature.onboarding.viewmodel.model.FormUiState
import com.example.vibeaway.ui.core.viewmodel.EventViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for form flow
 */
class FormViewModel(
    private val bfiQuestionsDataSource: BFIQuestionsDataSource,
    private val databaseRepository: DatabaseRepository
) : EventViewModel<FormUiState.Event>() {

    private val _uiState = MutableStateFlow(FormUiState())
    val uiState: StateFlow<FormUiState> = _uiState.asStateFlow()
        .onStart { loadQuestions() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = _uiState.value
        )

    /**
     * Retrieves the BFI questions and prepares them for display
     */
    private fun loadQuestions() {
        _uiState.update {
            val bfiQuestions = bfiQuestionsDataSource.getBfiQuestions()
            it.copy(bfiPages = bfiQuestions.chunked(QUESTIONS_PER_PAGE))
        }
    }

    /**
     * Registers or changes a response for a certain question
     */
    fun registerResponse(questionId: Int, responseScore: Int) {
        _uiState.update {
            val newResponses = it.responses.toMutableMap().apply {
                this[questionId] = responseScore
            }

            it.copy(responses = newResponses)
        }
    }

    /**
     * Computes the overall score for each BFI Dimension, save then into Firestore and ends
     * the onboarding flow
     */
    fun finishForm() = viewModelScope.launch {
        val questionsCount = _uiState.value.bfiPages.flatten().size
        val responsesCount = _uiState.value.responses.size

        when {
            questionsCount != responsesCount -> registerEvent(FormUiState.Event.NEXT_PAGE)

            else -> {
                val questions = _uiState.value.bfiPages.flatten()
                val groupedResponses = questions.mapNotNull { question ->
                    _uiState.value.responses[question.id]?.let { score ->
                        val signedScore = when {
                            question.reverseScore -> -score
                            else -> score
                        }

                        question.bfiDimension.id to signedScore
                    }
                }.groupBy({ it.first }, { it.second }).mapValues { (_, scores) -> scores.sum() }

                databaseRepository.saveBFIScores(groupedResponses)

                registerEvent(FormUiState.Event.FINISH_FORM)
            }
        }
    }

    companion object {
        private const val QUESTIONS_PER_PAGE = 3
    }
}
