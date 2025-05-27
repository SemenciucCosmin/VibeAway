package com.example.vibeaway.data.locationdetails.datasource

import com.example.vibeaway.data.locationdetails.model.LocationDetails
import kotlin.random.Random

/**
 * Data source for providing the list of [LocationDetails]
 */
class LocationDetailsDataSourceImpl : LocationDetailsDataSource {

    override fun getLocations(): List<LocationDetails> {
        return List(20) {
            LocationDetails(
                id = it.toString(),
                city = "City: $it",
                country = "Country: $it",
                latitude = 0.0,
                longitude = 0.0,
                imageUrl = listOf(
                    "https://i0.wp.com/www.touristitaly.com/wp-content/uploads/2023/03/Trevi-Fountain-rome-2-scaled.jpg?fit=4272%2C2848&ssl=1",
                    "https://static.independent.co.uk/2025/04/25/13/42/iStock-1498516775.jpg",
                    "https://www.aviontourism.com/images/1260-2600-fix/2c6f07a1-a448-468d-ae09-5a359122788b",
                    "https://www.aviontourism.com/images/1260-2600-fix/88b14ca1-730c-40fc-8e5d-dea72e8df912",
                    "https://visitsweden.com/_next/image/?url=https%3A%2F%2Fs3-eu-north-1.amazonaws.com%3A443%2Fpy3.visitsweden.com%2Foriginal_images%2FMalmo_220922-3461_IBSweb.jpg&w=1980&q=40",
                    "https://media.cntraveller.com/photos/66e81a194fe0349f1978d044/16:9/w_6240,h_3510,c_limit/Copenhagen-GettyImages-1628581451.jpg"
                ).random(),
                rating = Random.nextFloat() + (0..4).random(),
                activitiesIds = listOf(it.toString())
            )
        }
    }
}
