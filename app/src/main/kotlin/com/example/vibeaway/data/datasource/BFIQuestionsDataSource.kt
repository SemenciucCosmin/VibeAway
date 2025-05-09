package com.example.vibeaway.data.datasource

import com.example.vibeaway.data.model.BFIQuestion

/**
 * Data source for providing the list of [BFIQuestion] from a json file or from in memory cache.
 */
interface BFIQuestionsDataSource {

    /**
     * Parses a json file into a list of [BFIQuestion] or retrieves it from in memory cache.
     */
    fun getBfiQuestions(): List<BFIQuestion>
}
