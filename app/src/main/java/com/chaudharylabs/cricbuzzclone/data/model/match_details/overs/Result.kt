package com.chaudharylabs.cricbuzzclone.data.model.match_details.overs

data class Result(
    val resultType: String?,
    val winByInnings: Boolean?,
    val winByRuns: Boolean?,
    val winningMargin: Int?,
    val winningTeam: String?,
    val winningteamId: Int?
)