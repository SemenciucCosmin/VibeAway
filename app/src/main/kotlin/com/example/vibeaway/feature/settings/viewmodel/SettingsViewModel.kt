package com.example.vibeaway.feature.settings.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.vibeaway.data.auth.repository.AuthRepository
import com.example.vibeaway.data.database.repository.DatabaseRepository
import com.example.vibeaway.feature.settings.viewmodel.model.SettingsEvent
import com.example.vibeaway.ui.core.viewmodel.EventViewModel
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val databaseRepository: DatabaseRepository,
    private val authRepository: AuthRepository,
) : EventViewModel<SettingsEvent>() {

    fun resetBfiScores() = viewModelScope.launch {
        databaseRepository.removeBFIScores()
        registerEvent(SettingsEvent.START_ONBOARDING)
    }

    fun signOut() {
        authRepository.signOut()
        registerEvent(SettingsEvent.SIGN_OUT)
    }
}
