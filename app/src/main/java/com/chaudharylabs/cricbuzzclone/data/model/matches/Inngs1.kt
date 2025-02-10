package com.chaudharylabs.cricbuzzclone.data.model.matches

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Inngs1(
    val inningsId: Int?,
    var overs: Double?,
    var runs: Int?,
    var wickets: Int?
) : Parcelable