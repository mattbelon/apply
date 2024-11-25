package com.test.applydigital.data.models.news


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Fetch(
    @Json(name = "query")
    val query: Int,
    @Json(name = "total")
    val total: Int
)