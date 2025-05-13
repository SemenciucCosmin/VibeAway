package com.example.vibeaway.data.recommendation.datasource

import com.example.vibeaway.data.recommendation.model.Activity

/**
 * Data source for providing the list of [Activity] from a json file or from in memory cache.
 */
interface ActivitiesDataSource {

    /**
     * Parses a json file into a list of [Activity] or retrieves it from in memory cache.
     */
    fun getActivities(): List<Activity>
}
