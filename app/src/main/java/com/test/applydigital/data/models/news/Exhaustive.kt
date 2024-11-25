package com.test.applydigital.data.models.news


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Exhaustive(
    @Json(name = "nbHits")
    val nbHits: Boolean,
    @Json(name = "typo")
    val typo: Boolean
)