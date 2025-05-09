package com.example.vibeaway.data.model

/**
 * Data class for BFI question model
 * @param [id]: unique identifier of the object
 * @param [text]: question text content
 * @param [bfiDimension]: the BFI dimension to which the question belongs
 * @param [reverseScore]: boolean that determines if the question result score should be
 * positive of negative
 */
data class BFIQuestion(
    val id: Int,
    val text: String,
    val bfiDimension: BFIDimension,
    val reverseScore: Boolean
)
