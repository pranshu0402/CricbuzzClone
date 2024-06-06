package com.chaudharylabs.cricbuzzclone.data.model.match_details.overs

data class MatchUdrs(
    val inningsId: Int?,
    val matchId: Int?,
    val team1Id: Int?,
    val team1Remaining: Int?,
    val team1Successful: Int?,
    val team1Unsuccessful: Int?,
    val team2Id: Int?,
    val team2Remaining: Int?,
    val team2Successful: Int?,
    val team2Unsuccessful: Int?,
    val timestamp: String?
)