package com.example.vibeaway.data.model

data class Question(
    val id: Int,
    val text: String,
    val bfiDimension: BFIDimension,
    val reverseScore: Boolean
)
