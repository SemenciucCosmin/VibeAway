package com.example.vibeaway.feature.onboarding.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.vibeaway.data.database.repository.DatabaseRepository
import com.example.vibeaway.feature.onboarding.model.BFIDimension
import com.example.vibeaway.feature.onboarding.viewmodel.model.ManualInputUiState
import com.example.vibeaway.ui.core.viewmodel.EventViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ManualInputViewModel(
    private val databaseRepository: DatabaseRepository
) : EventViewModel<ManualInputUiState.Event>() {

    private val _uiState = MutableStateFlow(ManualInputUiState())
    val uiState: StateFlow<ManualInputUiState> = _uiState.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = _uiState.value
        )

    fun changeScore(dimension: BFIDimension, newScore: Int) {
        _uiState.update { uiState ->
            val newBFIDimensionsScores = BFIDimension.entries.associateWith {
                when {
                    it == dimension -> newScore
                    else -> uiState.bfiDimensionsScores[it] ?: MINIMUM_SCORE_VALUE
                }
            }

            uiState.copy(bfiDimensionsScores = newBFIDimensionsScores)
        }
    }

    fun saveScores() = viewModelScope.launch {
        val scores = _uiState.value.bfiDimensionsScores.mapKeys { (dimension, _) ->
            dimension.id
        }

        databaseRepository.saveBFIScores(scores)

        registerEvent(ManualInputUiState.Event.ONBOARDING_DONE)
    }

    companion object {
        private const val MINIMUM_SCORE_VALUE = 0
    }
}
