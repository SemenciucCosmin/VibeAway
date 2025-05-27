package com.example.vibeaway.feature.category.model

import androidx.annotation.StringRes
import com.example.vibeaway.R

enum class Category(
    @StringRes val titleRes: Int
) {
    RECOMMENDED_LOCATIONS(R.string.lbl_locations),
    RECOMMENDED_ACTIVITIES(R.string.lbl_activities),
    POPULAR_LOCATIONS(R.string.lbl_popular_title);

    companion object {
        fun getById(id: Int): Category {
            return entries.firstOrNull { it.ordinal == id } ?: POPULAR_LOCATIONS
        }
    }
}
