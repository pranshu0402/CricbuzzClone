package com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard

data class ScoreDetails(
    val ballNbr: Int?,
    val isDeclared: Boolean?,
    val isFollowOn: Boolean?,
    val overs: Double?,
    val revisedOvers: Double?,
    val runRate: Double?,
    val runs: Int?,
    val runsPerBall: Double?,
    val wickets: Int?
)