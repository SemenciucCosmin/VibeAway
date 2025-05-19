package com.example.vibeaway.feature.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vibeaway.R
import com.example.vibeaway.feature.onboarding.model.FormQuestionResponse
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

/**
 * Circle button for form question row
 */
@Composable
fun FormQuestionResponseButton(
    isSelected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(selectedColor)
            .size(40.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(
                    when {
                        isSelected -> selectedColor
                        else -> unselectedColor
                    }
                )
                .size(38.dp)
                .align(Alignment.Center)
        ) {
            if (isSelected) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    tint = Color.Black,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(30.dp)
                )
            }
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewFormQuestionResponseButtonUnselected() {
    VibeAwayTheme {
        FormQuestionResponseButton(
            isSelected = false,
            selectedColor = FormQuestionResponse.STRONGLY_AGREE.selectedColor,
            unselectedColor = FormQuestionResponse.STRONGLY_AGREE.unselectedColor,
            onClick = {},
        )
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewFormQuestionResponseButtonSelected() {
    VibeAwayTheme {
        FormQuestionResponseButton(
            isSelected = true,
            selectedColor = FormQuestionResponse.STRONGLY_DISAGREE.selectedColor,
            unselectedColor = FormQuestionResponse.STRONGLY_DISAGREE.unselectedColor,
            onClick = {},
        )
    }
}
