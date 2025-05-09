package com.example.vibeaway.data.model

enum class BFIDimension(val id: String) {
    OPENNESS(id = "O"),
    CONSCIENTIOUSNESS(id = "C"),
    EXTRAVERSION(id = "E"),
    AGREEABLENESS(id = "A"),
    NEUROTICISM(id = "N");

    companion object {
        fun getBFIDimensionById(id: String): BFIDimension? {
            return entries.firstOrNull { it.id == id }
        }
    }
}
