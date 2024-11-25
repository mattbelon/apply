package com.test.applydigital.ui.news

import com.test.applydigital.data.models.news.ApiResponse

sealed interface NewsUiState {
    data class Success(val apiResponse: ApiResponse): NewsUiState
    data class Error(val exception: Exception): NewsUiState
    object Loading: NewsUiState
}