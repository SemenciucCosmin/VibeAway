package com.example.vibeaway.data.activity.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO data class for activity category model
 * @param [id]: unique identifier of the object
 * @param [name]: name of the activity category model
 * @param [bfiDimensionsInfoDTOs]: list of related [BFIDimensionInfoDTO], should
 * always contain all 5 of them
 */
@Serializable
data class ActivityCategoryDTO(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("bfiDimensionsInfo") val bfiDimensionsInfoDTOs: List<BFIDimensionInfoDTO>? = null,
) {
    /**
     * DTO data class for BFI dimension model
     * @param [id]: unique identifier of the object
     * @param [regressionsWeight]: value with which the overall score increases on
     * each BFI dimension point
     */
    @Serializable
    data class BFIDimensionInfoDTO(
        @SerialName("id") val id: String? = null,
        @SerialName("regressionWeight") val regressionsWeight: Double? = null,
    )
}
