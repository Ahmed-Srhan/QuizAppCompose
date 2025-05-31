package com.example.quizappcompose.di

import com.example.domain.repository.QuizRepository
import com.example.domain.use_case.GetQuizzesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetQuizzesUseCase(repo: QuizRepository): GetQuizzesUseCase {
        return GetQuizzesUseCase(repo)
    }
}