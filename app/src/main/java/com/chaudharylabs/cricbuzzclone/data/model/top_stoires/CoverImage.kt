package com.chaudharylabs.cricbuzzclone.data.model.top_stoires

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoverImage(val id: String, val caption: String) : Parcelable
