package com.example.vibeaway.feature.onboarding.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.feature.onboarding.model.FormQuestionResponse
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

@Composable
fun FormQuestionRow(
    label: String,
    selectedResponse: FormQuestionResponse?,
    onResponseClick: (FormQuestionResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FormQuestionResponse.entries.forEach { formQuestionResponse ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(Spacing.Small),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.widthIn(max = Spacing.XXLarge)
                ) {
                    FormQuestionResponseButton(
                        isSelected = formQuestionResponse == selectedResponse,
                        selectedColor = formQuestionResponse.selectedColor,
                        unselectedColor = formQuestionResponse.unselectedColor,
                        onClick = { onResponseClick(formQuestionResponse) },
                    )

                    Text(
                        text = stringResource(formQuestionResponse.labelRes),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewFormQuestionRow() {
    VibeAwayTheme {
        FormQuestionRow(
            label = "I find it easy to build and maintain close, personal relationships",
            selectedResponse = FormQuestionResponse.AGREE,
            onResponseClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
