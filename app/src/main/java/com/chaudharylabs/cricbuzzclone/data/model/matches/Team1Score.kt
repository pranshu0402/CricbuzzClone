package com.chaudharylabs.cricbuzzclone.data.model.matches

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team1Score(
    val inngs1: Inngs1?,
    val inngs2: Inngs1?
):Parcelable