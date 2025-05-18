package com.example.vibeaway.feature.onboarding.viewmodel.model

import com.example.vibeaway.data.quiz.model.BFIQuestion
import com.example.vibeaway.ui.core.viewmodel.model.BasicEvent

data class FormUiState(
    val bfiPages: List<List<BFIQuestion>> = emptyList(),
    val responses: Map<Int, Int> = emptyMap()
) {

    enum class Event : BasicEvent {
        NEXT_PAGE,
        FINISH_FORM
    }
}
