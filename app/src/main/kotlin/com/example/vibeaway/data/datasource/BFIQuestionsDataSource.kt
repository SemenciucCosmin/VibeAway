package com.example.vibeaway.data.datasource

import com.example.vibeaway.data.model.BFIQuestion

interface BFIQuestionsDataSource {
    fun getBfiQuestions(): List<BFIQuestion>
}
