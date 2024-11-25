package com.test.applydigital.data.models.news


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Request(
    @Json(name = "roundTrip")
    val roundTrip: Int
)