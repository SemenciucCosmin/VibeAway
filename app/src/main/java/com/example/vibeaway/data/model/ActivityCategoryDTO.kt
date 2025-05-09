package com.example.vibeaway.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityCategoryDTO(
    @SerialName("id") val id: String?,
    @SerialName("name") val name: String?,
    @SerialName("bfiDimensionsInfo") val bfiDimensionsInfoDTOs: List<BFIDimensionInfoDTO>?
) {
    @Serializable
    data class BFIDimensionInfoDTO(
        @SerialName("id") val id: String?,
        @SerialName("regressionsWeight") val regressionsWeight: Double?
    )
}
