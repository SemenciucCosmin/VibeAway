package com.example.vibeaway.feature.onboarding.model

import androidx.annotation.StringRes
import com.example.vibeaway.R

/**
 * Enum class for all possible BFI dimensions
 * @param [id]: unique identifier of the object
 * @param [labelRes]: string resource as description for each dimension
 */
enum class BFIDimension(
    val id: String,
    @StringRes val labelRes: Int,
) {
    OPENNESS(
        id = "O",
        labelRes = R.string.lbl_openness
    ),
    CONSCIENTIOUSNESS(
        id = "C",
        labelRes = R.string.lbl_conscientiousness
    ),
    EXTRAVERSION(
        id = "E",
        labelRes = R.string.lbl_extraversion
    ),
    AGREEABLENESS(
        id = "A",
        labelRes = R.string.lbl_agreeableness
    ),
    NEUROTICISM(
        id = "N",
        labelRes = R.string.lbl_neuroticism
    )
}
