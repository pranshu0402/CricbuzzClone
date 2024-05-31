package com.chaudharylabs.cricbuzzclone.data.model.matches

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Inngs1(
    val inningsId: Int?,
    val overs: Double?,
    val runs: Int?,
    val wickets: Int?
) : Parcelable