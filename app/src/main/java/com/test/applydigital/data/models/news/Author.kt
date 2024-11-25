package com.test.applydigital.data.models.news


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Author(
    @Json(name = "matchLevel")
    val matchLevel: String,
    @Json(name = "matchedWords")
    val matchedWords: List<Any>,
    @Json(name = "value")
    val value: String
)