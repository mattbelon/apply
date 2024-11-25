package com.test.applydigital.data.models.news


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HighlightResult(
    @Json(name = "author")
    val author: Author,
    @Json(name = "comment_text")
    val commentText: CommentText?,
    @Json(name = "story_title")
    val storyTitle: StoryTitle?,
    @Json(name = "story_url")
    val storyUrl: StoryUrl?,
    @Json(name = "title")
    val title: Title?,
    @Json(name = "url")
    val url: Url?
)