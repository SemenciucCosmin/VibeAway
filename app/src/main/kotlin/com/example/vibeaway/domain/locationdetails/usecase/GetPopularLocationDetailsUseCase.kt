package com.example.vibeaway.domain.locationdetails.usecase

import com.example.vibeaway.data.locationdetails.datasource.LocationDetailsDataSource
import com.example.vibeaway.data.locationdetails.model.LocationDetails

/**
 * Use case for fetching the list of popular [LocationDetails]
 */
class GetPopularLocationDetailsUseCase(
    private val locationDetailsDataSource: LocationDetailsDataSource
) {
    operator fun invoke(): List<LocationDetails> {
        return locationDetailsDataSource.getLocations().sortedBy { it.rating }
    }
}
