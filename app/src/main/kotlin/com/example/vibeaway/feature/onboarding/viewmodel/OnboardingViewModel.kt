package com.example.vibeaway.feature.onboarding.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.vibeaway.data.database.repository.DatabaseRepository
import com.example.vibeaway.feature.onboarding.viewmodel.model.OnboardingEvent
import com.example.vibeaway.ui.core.viewmodel.EventViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val databaseRepository: DatabaseRepository
) : EventViewModel<OnboardingEvent>() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
        .onStart { checkBFIScore() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = _isLoading.value
        )

    private fun checkBFIScore() = viewModelScope.launch {
        if (databaseRepository.getBFIScores() != null) {
            registerEvent(OnboardingEvent.ONBOARDING_DONE)
        } else {
            _isLoading.update { false }
        }
    }
}
