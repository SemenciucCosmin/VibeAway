package com.example.vibeaway.feature.onboarding.viewmodel.model

import com.example.vibeaway.feature.onboarding.model.BFIDimension
import com.example.vibeaway.ui.core.viewmodel.model.BasicEvent

/**
 * Ui state class for manual input flow
 * @param [bfiDimensionsScores]: map of each BFI dimension and its manual score
 */
data class ManualInputUiState(
    val bfiDimensionsScores: Map<BFIDimension, Int> = BFIDimension.entries.associateWith {
        MINIMUM_SCORE_VALUE
    }
) {

    /**
     * Enum class for all events in the manual input flow
     */
    enum class Event : BasicEvent {
        ONBOARDING_DONE
    }

    companion object {
        private const val MINIMUM_SCORE_VALUE = 0
    }
}
