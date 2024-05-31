package com.chaudharylabs.cricbuzzclone.data.model.matches

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Matche(
    val matchInfo: MatchInfo?,
    val matchScore: MatchScore?
) : Parcelable