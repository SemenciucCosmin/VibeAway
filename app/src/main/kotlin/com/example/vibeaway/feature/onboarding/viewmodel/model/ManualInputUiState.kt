package com.example.vibeaway.feature.onboarding.viewmodel.model

import com.example.vibeaway.feature.onboarding.model.BFIDimension
import com.example.vibeaway.ui.core.viewmodel.model.BasicEvent

data class ManualInputUiState(
    val bfiDimensionsScores: Map<BFIDimension, Int> = BFIDimension.entries.associateWith {
        MINIMUM_SCORE_VALUE
    }
) {

    enum class Event : BasicEvent {
        ONBOARDING_DONE
    }

    companion object {
        private const val MINIMUM_SCORE_VALUE = 0
    }
}
