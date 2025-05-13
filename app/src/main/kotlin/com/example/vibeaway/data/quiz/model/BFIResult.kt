package com.example.vibeaway.data.quiz.model

/**
 * Data class for BFI question result model
 * @param [bfiDimension]: the BFI dimension to which the result belongs
 * @param [score]: score value of the result
 */
data class BFIResult(
    val bfiDimension: BFIDimension,
    val score: Int,
)
