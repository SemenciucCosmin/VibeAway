package com.example.vibeaway.data.location.datasource

import com.example.vibeaway.data.location.model.Location

/**
 * Data source for providing the list of [Location] from a json file or from in memory cache.
 */
interface LocationsDataSource {

    /**
     * Parses a json file into a list of [Location] or retrieves it from in memory cache.
     */
    fun getLocations(): List<Location>
}
