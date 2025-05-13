package com.example.vibeaway.data.recommendation.datasource

import com.example.vibeaway.data.core.datasource.JsonDataSource
import com.example.vibeaway.data.recommendation.model.Location
import com.example.vibeaway.data.recommendation.model.LocationDTO
import kotlinx.serialization.json.Json
import java.util.Collections
import kotlin.collections.isNotEmpty

/**
 * Data source for providing the list of [Location] from a json file or from in memory cache.
 */
class LocationsDataSourceImpl : LocationsDataSource, JsonDataSource() {

    /**
     * In memory cache for list of [Location]
     */
    private val locations: MutableList<Location> = Collections.synchronizedList(mutableListOf())

    /**
     * Parses a json file into a list of [Location] or retrieves it from in memory cache.
     */
    override fun getLocations(): List<Location> {
        return when {
            this.locations.isNotEmpty() -> this.locations

            else -> {
                val locationsFromFile = getLocationsFromFile()
                this.locations.clear()
                this.locations.addAll(locationsFromFile)
                locationsFromFile
            }
        }
    }

    /**
     * Parses a json file into a list of [LocationDTO] and maps it to a list of [Location].
     */
    private fun getLocationsFromFile(): List<Location> {
        val jsonString = getJson(FILE_PATH)
        val locationDTOs = Json.decodeFromString<List<LocationDTO>>(jsonString)
        return mapLocationDTOtoLocation(locationDTOs)
    }

    /**
     * Maps a list of [LocationDTO] to a list of [Location].
     */
    private fun mapLocationDTOtoLocation(locationDTOs: List<LocationDTO>): List<Location> {
        return locationDTOs.mapNotNull { locationDTO ->
            Location(
                city = locationDTO.city ?: return@mapNotNull null,
                country = locationDTO.country ?: return@mapNotNull null,
                latitude = locationDTO.latitude ?: return@mapNotNull null,
                longitude = locationDTO.longitude ?: return@mapNotNull null,
            )
        }
    }

    companion object {
        private const val FILE_PATH = "locations.json"
    }
}
