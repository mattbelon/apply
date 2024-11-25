package com.test.applydigital.data.models.news


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Url(
    @Json(name = "fullyHighlighted")
    val fullyHighlighted: Boolean?,
    @Json(name = "matchLevel")
    val matchLevel: String,
    @Json(name = "matchedWords")
    val matchedWords: List<String>,
    @Json(name = "value")
    val value: String
)