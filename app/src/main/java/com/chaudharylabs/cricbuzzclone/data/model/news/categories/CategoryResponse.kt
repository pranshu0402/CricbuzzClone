package com.chaudharylabs.cricbuzzclone.data.model.news.categories


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CategoryResponse(
    @SerializedName("storyType")
    val storyType: List<StoryType> = listOf()
) {

    @Parcelize
    data class StoryType(
        @SerializedName("description")
        val description: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("name")
        val name: String = ""
    ) : Parcelable
}