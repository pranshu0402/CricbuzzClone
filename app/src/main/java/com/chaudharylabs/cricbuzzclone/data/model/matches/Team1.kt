package com.chaudharylabs.cricbuzzclone.data.model.matches

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team1(
    val imageId: Int?,
    val teamId: Int?,
    val teamName: String?,
    val teamSName: String?
) : Parcelable