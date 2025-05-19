package com.example.vibeaway.feature.onboarding.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.vibeaway.R
import com.example.vibeaway.data.quiz.model.BFIQuestion
import com.example.vibeaway.feature.onboarding.model.FormQuestionResponse
import com.example.vibeaway.ui.catalog.components.PrimaryButton
import com.example.vibeaway.ui.catalog.dimension.Spacing

/**
 * Reusable screen for onboarding form flow
 */
@Composable
fun FormScreen(
    pageCount: Int,
    selectedPageIndex: Int,
    questions: List<BFIQuestion>,
    responses: Map<Int, Int>,
    isNextButtonEnabled: Boolean,
    onResponseClick: (Int, Int) -> Unit,
    onNextClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.XXLarge),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_left),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null,
                    )
                }

                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.lbl_page_counter, selectedPageIndex + 1, pageCount),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Spacer(modifier = Modifier.height(Spacing.XLarge))

            Text(
                text = stringResource(R.string.lbl_form_questions_message),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(Spacing.Medium))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                questions.forEach { question ->
                    FormQuestionRow(
                        modifier = Modifier.fillMaxWidth(),
                        label = question.text,
                        onResponseClick = { onResponseClick(question.id, it.score) },
                        selectedResponse = FormQuestionResponse.getByScore(
                            score = responses.entries.firstOrNull { it.key == question.id }?.value
                        )
                    )
                }
            }

            PrimaryButton(
                text = stringResource(R.string.lbl_next),
                onClick = onNextClick,
                enabled = isNextButtonEnabled,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
