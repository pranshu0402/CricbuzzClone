package com.chaudharylabs.cricbuzzclone.data.model.matches

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VenueInfo(
    val city: String?,
    val ground: String?,
    val id: Int?,
    val latitude: String?,
    val longitude: String?,
    val timezone: String?
) : Parcelable