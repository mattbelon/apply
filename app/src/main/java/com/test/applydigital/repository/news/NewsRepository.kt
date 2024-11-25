package com.test.applydigital.repository.news

import com.test.applydigital.data.models.news.ApiResponse
import com.test.applydigital.data.remote.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository {
    private val apiService: ApiService = ApiService.createApiService()
    suspend fun getNews(): ApiResponse = withContext(Dispatchers.IO) {
        return@withContext apiService.getNewsByDate()
    }
}