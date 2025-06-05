package com.example.vibeaway.domain.locationdetails.usecase

import com.example.vibeaway.data.activitydetails.datasource.ActivityDetailsDataSource
import com.example.vibeaway.data.location.datasource.LocationsDataSource
import com.example.vibeaway.data.locationdetails.model.LocationDetails

/**
 * Use case for fetching the list of popular [LocationDetails]
 */
class GetLocationDetailsUseCase(
    private val locationsDataSource: LocationsDataSource,
    private val activityDetailsDataSource: ActivityDetailsDataSource,
) {
    suspend operator fun invoke(): List<LocationDetails> {
        val locations = locationsDataSource.getLocations()
        val locationsWithActivitiesDetails = activityDetailsDataSource.getActivitiesDetails(
            forceApi = false,
            forceGoogle = false
        )

        val locationsDetails = locations.map { location ->
            LocationDetails(
                id = location.id,
                city = location.city,
                country = location.country,
                description = location.description,
                latitude = location.latitude,
                longitude = location.longitude,
                imageFileName = location.imageFileName,
                activitiesDetails = locationsWithActivitiesDetails[location.id] ?: emptyList()
            )
        }

        return locationsDetails
    }
}
