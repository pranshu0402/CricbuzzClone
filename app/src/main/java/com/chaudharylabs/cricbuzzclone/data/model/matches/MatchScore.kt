package com.chaudharylabs.cricbuzzclone.data.model.matches

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchScore(
    val team1Score: Team1Score?,
    val team2Score: Team1Score?
) : Parcelable