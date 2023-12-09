package com.assignment.stocksbrowser.di

import com.assignment.stocksbrowser.api.NewsApi
import com.assignment.stocksbrowser.api.NewsApi.Companion.BASE_URL
import com.assignment.stocksbrowser.api.NewsRepositoryImpl
import com.assignment.stocksbrowser.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppNewsModule {

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository {
        return NewsRepositoryImpl(newsApi = newsApi)
    }
}