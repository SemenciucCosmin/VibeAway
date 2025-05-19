package com.example.vibeaway.feature.onboarding.di

import com.example.vibeaway.feature.onboarding.viewmodel.FormViewModel
import org.koin.core.Koin
import org.koin.core.qualifier.named

/**
 * Object for managing the koin score for [FormViewModel]
 */
object FormScope {

    const val ID = "FORM_SCOPE"

    /**
     * Creates the scope
     */
    fun create(koin: Koin) {
        delete(koin)
        koin.createScope(ID, named(ID))
    }

    /**
     * Deletes the scope
     */
    fun delete(koin: Koin) {
        koin.getScopeOrNull(ID)?.close()
    }
}
