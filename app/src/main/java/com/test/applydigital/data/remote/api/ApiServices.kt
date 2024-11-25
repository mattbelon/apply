package com.test.applydigital.data.remote.api

import com.squareup.moshi.Moshi
import com.test.applydigital.Constants
import com.test.applydigital.data.models.news.ApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("search_by_date?query=mobile/")
    suspend fun getNewsByDate(): ApiResponse

    companion object {
        fun createApiService(): ApiService {
            val moshi = Moshi.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}