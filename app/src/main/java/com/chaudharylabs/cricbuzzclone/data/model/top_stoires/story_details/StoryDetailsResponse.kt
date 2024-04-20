package com.chaudharylabs.cricbuzzclone.data.model.top_stoires.story_details

data class StoryDetailsResponse(
    val appIndex: AppIndex?,
    val authors: List<Author>?,
    val content: List<Content>?,
    val context: String?,
    val coverImage: CoverImage?,
    val entitlements: Entitlements?,
    val format: List<Format>?,
    val headline: String?,
    val id: Int?,
    val intro: String?,
    val lastUpdatedTime: String?,
    val publishTime: String?,
    val source: String?,
    val storyType: String?,
    val tags: List<Tag>?
)