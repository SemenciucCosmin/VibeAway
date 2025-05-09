package com.example.vibeaway.data.model

data class BFIQuestion(
    val id: Int,
    val text: String,
    val bfiDimension: BFIDimension,
    val reverseScore: Boolean
)
