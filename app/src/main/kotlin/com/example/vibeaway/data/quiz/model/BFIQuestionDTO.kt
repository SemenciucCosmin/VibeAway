package com.example.vibeaway.data.quiz.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO data class for BFI question model
 * @param [id]: unique identifier of the object
 * @param [text]: question text content
 * @param [bfiDimensionId]: the BFI dimension unique identifier to which the question belongs
 * @param [reverseScore]: boolean that determines if the question result score should be
 * positive of negative
 */
@Serializable
data class BFIQuestionDTO(
    @SerialName("id") val id: Int? = null,
    @SerialName("text") val text: String? = null,
    @SerialName("bfiDimensionId") val bfiDimensionId: String? = null,
    @SerialName("reverseScore") val reverseScore: Boolean = false
)
