package com.example.vibeaway.data.quiz.datasource

import com.example.vibeaway.data.core.datasource.JsonDataSource
import com.example.vibeaway.data.quiz.model.BFIDimension
import com.example.vibeaway.data.quiz.model.BFIQuestion
import com.example.vibeaway.data.quiz.model.BFIQuestionDTO
import kotlinx.serialization.json.Json
import java.util.Collections

/**
 * Data source for providing the list of [BFIQuestion] from a json file or from in memory cache.
 */
class BFIQuestionsDataSourceImpl : BFIQuestionsDataSource, JsonDataSource() {

    /**
     * In memory cache for list of [BFIQuestion]
     */
    private val bfiQuestions: MutableList<BFIQuestion> =
        Collections.synchronizedList(mutableListOf())

    /**
     * Parses a json file into a list of [BFIQuestion] or retrieves it from in memory cache.
     */
    override fun getBfiQuestions(): List<BFIQuestion> {
        return when {
            this.bfiQuestions.isNotEmpty() -> this.bfiQuestions

            else -> {
                val bfiQuestionsFromFile = getBfiQuestionsFromFile()
                this.bfiQuestions.clear()
                this.bfiQuestions.addAll(bfiQuestionsFromFile)
                bfiQuestionsFromFile
            }
        }
    }

    /**
     * Parses a json file into a list of [BFIQuestionDTO] and maps it to a list of [BFIQuestion].
     */
    private fun getBfiQuestionsFromFile(): List<BFIQuestion> {
        val jsonString = getJsonFromResources(FILE_PATH)
        val bfiQuestionDTOs = Json.decodeFromString<List<BFIQuestionDTO>>(jsonString)
        return mapBFIQuestionDTOtoBFIQuestion(bfiQuestionDTOs)
    }

    /**
     * Maps a list of [BFIQuestionDTO] to a list of [BFIQuestion].
     */
    private fun mapBFIQuestionDTOtoBFIQuestion(
        bfiQuestionDTOs: List<BFIQuestionDTO>
    ): List<BFIQuestion> {
        return bfiQuestionDTOs.mapNotNull { bfiQuestionDTO ->
            val bfiDimensionId = bfiQuestionDTO.bfiDimensionId ?: return@mapNotNull null
            val bfiDimension = BFIDimension.getBFIDimensionById(bfiDimensionId)

            BFIQuestion(
                id = bfiQuestionDTO.id ?: return@mapNotNull null,
                text = bfiQuestionDTO.text ?: return@mapNotNull null,
                bfiDimension = bfiDimension ?: return@mapNotNull null,
                reverseScore = bfiQuestionDTO.reverseScore,
            )
        }
    }

    companion object {
        private const val FILE_PATH = "big_five_inventory_questions.json"
    }
}
