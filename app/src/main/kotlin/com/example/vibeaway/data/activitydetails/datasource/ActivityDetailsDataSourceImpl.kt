package com.example.vibeaway.data.activitydetails.datasource

import com.example.vibeaway.data.activitydetails.model.ActivityDetails

/**
 * Data source for providing the list of [ActivityDetails]
 */
class ActivityDetailsDataSourceImpl : ActivityDetailsDataSource {

    override fun getActivitiesDetails(): List<ActivityDetails> {
        return List(20) {
            ActivityDetails(
                id = it.toString(),
                title = "Title: $it",
                city = "City: $it",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                imageUrl = listOf(
                    "https://gmgc.com/wp-content/uploads/2024/04/Ultimate-Guide-To-Indoor-Rock-Climbing-For-Kids_770x400-770x400.jpg",
                    "https://images.giant-bicycles.com/xum4rnsmri9byrk75pz4/preview.jpg",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQL1euBJAwoiFO-cVU4du5lqkcwmzG7FjF2uA&s",
                    "https://denblaaplanet.dk/wp-content/uploads/2025/05/1.png",
                    "https://images.stockcake.com/public/7/0/a/70aec8d4-e329-417f-9da0-cf271c3c2278_large/disco-party-vibes-stockcake.jpg",
                    "https://static.vecteezy.com/system/resources/previews/000/362/602/non_2x/cute-animals-in-the-zoo-vector.jpg"
                ).random()
            )
        }
    }
}
