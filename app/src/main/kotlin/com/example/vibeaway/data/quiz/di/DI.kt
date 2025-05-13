package com.example.vibeaway.data.quiz.di

import com.example.vibeaway.data.quiz.datasource.BFIQuestionsDataSource
import com.example.vibeaway.data.quiz.datasource.BFIQuestionsDataSourceImpl
import org.koin.dsl.module

fun quizDataModule() = module {
    single<BFIQuestionsDataSource> { BFIQuestionsDataSourceImpl() }
}
