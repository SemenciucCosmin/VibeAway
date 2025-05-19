package com.example.vibeaway.feature.onboarding.route

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.vibeaway.feature.onboarding.component.FormScreen
import com.example.vibeaway.feature.onboarding.di.FormScope
import com.example.vibeaway.feature.onboarding.navigation.model.OnboardingNavDestination
import com.example.vibeaway.feature.onboarding.viewmodel.FormViewModel
import com.example.vibeaway.feature.onboarding.viewmodel.model.FormUiState
import com.example.vibeaway.ui.activity.MainActivity
import com.example.vibeaway.ui.activity.OnboardingActivity
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.core.components.EventHandler
import org.koin.compose.getKoin

/**
 * Route for form onboarding flow
 */
@Composable
fun FormRoute(
    index: Int,
    navController: NavController,
) {
    val koin = getKoin()
    val viewModel = koin.getScope(FormScope.ID).get<FormViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val questions = uiState.bfiPages.getOrNull(index) ?: return
    val activity = LocalActivity.current as? OnboardingActivity
    val responsesCount = uiState.responses.filter { (questionId, _) ->
        questionId in questions.map { it.id }
    }.size

    FormScreen(
        pageCount = uiState.bfiPages.size,
        selectedPageIndex = index,
        questions = questions,
        responses = uiState.responses,
        isNextButtonEnabled = responsesCount == questions.size,
        onResponseClick = viewModel::registerResponse,
        onNextClick = viewModel::finishForm,
        onBack = navController::navigateUp,
        modifier = Modifier.padding(
            top = Spacing.XXLarge,
            bottom = Spacing.Large,
            start = Spacing.Large,
            end = Spacing.Large,
        )
    )

    EventHandler(viewModel.events) { event ->
        when (event) {
            FormUiState.Event.NEXT_PAGE -> {
                val nextPageIndex = index + 1
                viewModel.unregisterEvent(event)
                navController.navigate(OnboardingNavDestination.Form(nextPageIndex))
            }
            FormUiState.Event.FINISH_FORM -> {
                viewModel.unregisterEvent(event)
                FormScope.delete(koin)
                activity?.let(MainActivity::startActivity)
            }
        }
    }
}
