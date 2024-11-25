package com.test.applydigital.data.models.news


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "exhaustive")
    val exhaustive: Exhaustive,
    @Json(name = "exhaustiveNbHits")
    val exhaustiveNbHits: Boolean,
    @Json(name = "exhaustiveTypo")
    val exhaustiveTypo: Boolean,
    @Json(name = "hits")
    val hits: List<Hit>,
    @Json(name = "hitsPerPage")
    val hitsPerPage: Int,
    @Json(name = "nbHits")
    val nbHits: Int,
    @Json(name = "nbPages")
    val nbPages: Int,
    @Json(name = "page")
    val page: Int,
    @Json(name = "params")
    val params: String,
    @Json(name = "processingTimeMS")
    val processingTimeMS: Int,
    @Json(name = "processingTimingsMS")
    val processingTimingsMS: ProcessingTimingsMS,
    @Json(name = "query")
    val query: String,
    @Json(name = "serverTimeMS")
    val serverTimeMS: Int
)