package com.test.applydigital.data.models.news


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProcessingTimingsMS(
    @Json(name = "afterFetch")
    val afterFetch: AfterFetch? = null,
    @Json(name = "fetch")
    val fetch: Fetch,
    @Json(name = "_request")
    val request: Request,
    @Json(name = "total")
    val total: Int
)

