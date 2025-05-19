package com.example.vibeaway.feature.onboarding.di

import org.koin.core.Koin
import org.koin.core.qualifier.named

object FormScope {

    const val ID = "FORM_SCOPE"

    fun create(koin: Koin) {
        delete(koin)
        koin.createScope(ID, named(ID))
    }

    fun delete(koin: Koin) {
        koin.getScopeOrNull(ID)?.close()
    }
}
