package com.test.applydigital.data.models.news


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hit(
    @Json(name = "author")
    val author: String,
    @Json(name = "children")
    val children: List<Int>?,
    @Json(name = "comment_text")
    val commentText: String?,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "created_at_i")
    val createdAtI: Int,
    @Json(name = "_highlightResult")
    val highlightResult: HighlightResult,
    @Json(name = "num_comments")
    val numComments: Int?,
    @Json(name = "objectID")
    val objectID: String,
    @Json(name = "parent_id")
    val parentId: Int?,
    @Json(name = "points")
    val points: Int?,
    @Json(name = "story_id")
    val storyId: Int,
    @Json(name = "story_title")
    val storyTitle: String?,
    @Json(name = "story_url")
    val storyUrl: String?,
    @Json(name = "_tags")
    val tags: List<String>,
    @Json(name = "title")
    val title: String?,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "url")
    val url: String?
)