package com.example.vibeaway.data.model

/**
 * Data class for activity category model
 * @param [id]: unique identifier of the object
 * @param [name]: name of the activity category model
 * @param [bfiDimensionsInfo]: list of related [BFIDimensionInfo], should always
 * contain all 5 of them
 */
data class ActivityCategory(
    val id: String,
    val name: String,
    val bfiDimensionsInfo: List<BFIDimensionInfo>
) {
    /**
     * Data class for BFI dimension model
     * @param [id]: unique identifier of the object
     * @param [regressionsWeight]: value with which the overall score increases on
     * each BFI dimension point
     */
    data class BFIDimensionInfo(
        val id: String,
        val regressionsWeight: Double
    )
}
