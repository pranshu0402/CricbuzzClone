package com.chaudharylabs.cricbuzzclone.data.model.match_details.overs

data class InningsScore(
    val ballNbr: Int?,
    val batTeamId: Int?,
    val batTeamName: String?,
    val inningsId: Int?,
    val isDeclared: Boolean?,
    val isFollowOn: Boolean?,
    val overs: Double?,
    val score: Int?,
    val wickets: Int?
)