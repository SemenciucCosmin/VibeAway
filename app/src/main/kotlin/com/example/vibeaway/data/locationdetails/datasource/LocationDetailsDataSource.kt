package com.example.vibeaway.data.locationdetails.datasource

import com.example.vibeaway.data.locationdetails.model.LocationDetails

/**
 * Data source for providing the list of [LocationDetails]
 */
interface LocationDetailsDataSource {

    fun getLocationsDetails(): List<LocationDetails>
}
