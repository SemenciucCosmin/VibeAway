package com.example.vibeaway.data.datasource

import com.example.vibeaway.data.model.ActivityCategory

interface ActivityCategoriesDataSource {
    fun getActivityCategories(): List<ActivityCategory>
}
