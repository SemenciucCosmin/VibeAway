package com.example.vibeaway.feature.onboarding.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.vibeaway.data.database.repository.DatabaseRepository
import com.example.vibeaway.feature.onboarding.viewmodel.model.OnboardingEvent
import com.example.vibeaway.ui.core.viewmodel.EventViewModel
import kotlinx.coroutines.launch

class OnboardingViewModel(
    databaseRepository: DatabaseRepository
) : EventViewModel<OnboardingEvent>() {
    init {
        viewModelScope.launch {
            if (databaseRepository.getBFIScores() != null) {
                registerEvent(OnboardingEvent.ONBOARDING_DONE)
            }
        }
    }
}
