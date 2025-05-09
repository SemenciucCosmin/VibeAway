package com.example.vibeaway.data.datasource

import com.example.vibeaway.data.model.ActivityCategory

/**
 * Data source for providing the list of [ActivityCategory] from a json file or from in memory cache.
 */
interface ActivityCategoriesDataSource {

    /**
     * Parses a json file into a list of [ActivityCategory] or retrieves it from in memory cache.
     */
    fun getActivityCategories(): List<ActivityCategory>
}
