package com.example.vibeaway.data.activity.datasource

import com.example.vibeaway.data.activity.model.ActivityInfo

/**
 * Data source for providing the list of [ActivityInfo] from a json file or from in memory cache.
 */
interface ActivityCategoriesDataSource {

    /**
     * Parses a json file into a list of [ActivityInfo] or retrieves it from in memory cache.
     */
    fun getActivityCategories(): List<ActivityInfo>
}
