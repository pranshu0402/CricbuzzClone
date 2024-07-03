package com.chaudharylabs.cricbuzzclone.data.model.news.topics


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TopicsResponse(
    @SerializedName("topics")
    val topics: List<Topic> = listOf()
) {

    @Parcelize
    data class Topic(
        @SerializedName("description")
        val description: String = "",
        @SerializedName("headline")
        val headline: String = "",
        @SerializedName("id")
        val id: Int = 0
    ) : Parcelable
}