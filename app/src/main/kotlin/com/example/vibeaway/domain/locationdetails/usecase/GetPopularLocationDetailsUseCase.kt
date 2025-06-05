package com.example.vibeaway.domain.locationdetails.usecase

import com.example.vibeaway.data.locationdetails.model.LocationDetails
import com.example.vibeaway.data.repository.RecommendationRepository

/**
 * Use case for fetching the list of popular [LocationDetails]
 */
class GetPopularLocationDetailsUseCase(
    private val recommendationRepository: RecommendationRepository
) {
    suspend operator fun invoke(): List<LocationDetails> {
        val locationsDetails = recommendationRepository.getRecommendedLocationsDetails()
        val sortedLocationsDetails = locationsDetails.sortedBy { locationDetails ->
            val activitiesRatings = locationDetails.activitiesDetails.map { it.rating }
            val score = activitiesRatings.sum()
            score
        }

        return sortedLocationsDetails
    }
}
