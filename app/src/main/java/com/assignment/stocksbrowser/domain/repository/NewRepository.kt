package com.assignment.stocksbrowser.domain.repository

import com.assignment.stocksbrowser.domain.model.Article
import com.assignment.stocksbrowser.utils.Resource

interface NewsRepository {

    suspend fun getTopHeadlines(
        category: String
    ): Resource<List<Article>>


    suspend fun searchForNews(
        query: String
    ): Resource<List<Article>>
}