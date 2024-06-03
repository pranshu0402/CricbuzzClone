package com.chaudharylabs.cricbuzzclone.data.model.match_details.squads

import com.google.gson.annotations.SerializedName

data class Players(
    val bench: List<Bench?>?,
    @SerializedName("playing XI")
    val playingXI: List<PlayingXI?>?
)