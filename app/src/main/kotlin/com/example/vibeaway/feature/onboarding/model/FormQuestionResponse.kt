package com.example.vibeaway.feature.onboarding.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.vibeaway.R

/**
 * Enum class for all possible question responses
 * @param [score]: value scored for each response
 * @param [selectedColor]: color for selected state
 * @param [unselectedColor]: color for unselected state
 * @param [labelRes]: string resource as description for each response
 */
enum class FormQuestionResponse(
    val score: Int,
    val selectedColor: Color,
    val unselectedColor: Color,
    @StringRes val labelRes: Int
) {
    STRONGLY_DISAGREE(
        score = 1,
        selectedColor = Color(0xFFE2978B),
        unselectedColor = Color(0xFFFCE9E6),
        labelRes = R.string.lbl_strongly_disagree
    ),
    DISAGREE(
        score = 2,
        selectedColor = Color(0xFFD9AB85),
        unselectedColor = Color(0xFFFDF1E7),
        labelRes = R.string.lbl_disagree
    ),
    NEUTRAL(
        score = 3,
        selectedColor = Color(0xFFC8C8C8),
        unselectedColor = Color(0xFFF9FAFA),
        labelRes = R.string.lbl_neutral
    ),
    AGREE(
        score = 4,
        selectedColor = Color(0xFFB7C2B7),
        unselectedColor = Color(0xFFE7F7E7),
        labelRes = R.string.lbl_agree
    ),
    STRONGLY_AGREE(
        score = 5,
        selectedColor = Color(0xFF86CFB1),
        unselectedColor = Color(0xFFDEF4EB),
        labelRes = R.string.lbl_strongly_agree
    );

    companion object {
        fun getByScore(score: Int?): FormQuestionResponse? {
            return entries.firstOrNull { it.score == score }
        }
    }
}
