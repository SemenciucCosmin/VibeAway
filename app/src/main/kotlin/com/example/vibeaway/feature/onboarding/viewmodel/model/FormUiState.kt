package com.example.vibeaway.feature.onboarding.viewmodel.model

import com.example.vibeaway.data.quiz.model.BFIQuestion
import com.example.vibeaway.ui.core.viewmodel.model.BasicEvent

/**
 * Ui state class for form flow
 * @param [bfiPages]: list of sublists of questions for each form page
 * @param [responses]: map of all registered answers for all questions
 */
data class FormUiState(
    val bfiPages: List<List<BFIQuestion>> = emptyList(),
    val responses: Map<Int, Int> = emptyMap()
) {

    /**
     * Enum class for all events in the form flow
     */
    enum class Event : BasicEvent {
        NEXT_PAGE,
        FINISH_FORM
    }
}
