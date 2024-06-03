package com.chaudharylabs.cricbuzzclone.data.model.match_details.info

data class PlayersOfTheMatch(
    val captain: Boolean?,
    val faceImageId: Int?,
    val fullName: String?,
    val id: Int?,
    val keeper: Boolean?,
    val name: String?,
    val nickName: String?,
    val substitute: Boolean?,
    val teamName: String?
)