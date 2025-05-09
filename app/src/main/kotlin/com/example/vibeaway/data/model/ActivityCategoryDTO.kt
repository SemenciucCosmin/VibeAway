package com.example.vibeaway.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityCategoryDTO(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("bfiDimensionsInfo") val bfiDimensionsInfoDTOs: List<BFIDimensionInfoDTO>? = null,
) {
    @Serializable
    data class BFIDimensionInfoDTO(
        @SerialName("id") val id: String? = null,
        @SerialName("regressionWeight") val regressionsWeight: Double? = null,
    )
}
