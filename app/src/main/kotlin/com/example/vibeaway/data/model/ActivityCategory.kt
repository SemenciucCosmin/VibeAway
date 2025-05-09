package com.example.vibeaway.data.model

data class ActivityCategory(
    val id: String,
    val name: String,
    val bfiDimensionsInfo: List<BFIDimensionInfo>
) {
    data class BFIDimensionInfo(
        val id: String,
        val regressionsWeight: Double
    )
}
