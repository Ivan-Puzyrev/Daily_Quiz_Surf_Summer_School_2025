package com.ivanpuzyrev.dailyquizsurfsummerschool2025.di

import android.app.Application
import com.ivanpuzyrev.data.QuizRepositoryImpl
import com.ivanpuzyrev.data.retrofit.ApiService
import com.ivanpuzyrev.data.retrofit.RetrofitObject
import com.ivanpuzyrev.data.room.AppDatabase
import com.ivanpuzyrev.data.room.GameResultsDao
import com.ivanpuzyrev.domain.QuizRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

    @Binds
    @Singleton
    fun bindRepository(impl: QuizRepositoryImpl): QuizRepository

    companion object{
        @Provides
        @Singleton
        fun provideApiService(): ApiService {
            return RetrofitObject.apiService
        }

        @Provides
        @Singleton
        fun provideGameResultsDao(application: Application): GameResultsDao {
            return AppDatabase.getInstance(application).gameResultsDao()
        }

    }
}