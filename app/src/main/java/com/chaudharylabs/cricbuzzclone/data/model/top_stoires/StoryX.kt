package com.chaudharylabs.cricbuzzclone.data.model.top_stoires

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryX(
    val context: String?,
    val hline: String?,
    val id: Int?,
    val imageId: Int?,
    val intro: String?,
    val pubTime: String?,
    val seoHeadline: String?,
    val source: String?,
    val storyType: String?,
    val coverImage: CoverImage?
):Parcelable