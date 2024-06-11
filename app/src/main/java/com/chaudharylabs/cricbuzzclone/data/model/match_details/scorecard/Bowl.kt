package com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard

data class Bowl(
    val balls: Int?,
    val bowlName: String?,
    val bowlShortName: String?,
    val bowlerId: Int?,
    val dots: Int?,
    val economy: Double?,
    val inMatchChange: String?,
    val isCaptain: Boolean?,
    val isKeeper: Boolean?,
    val isOverseas: Boolean?,
    val maidens: Int?,
    val no_balls: Int?,
    val overs: Double?,
    val playingXIChange: String?,
    val runs: Int?,
    val runsPerBall: Double?,
    val wickets: Int?,
    val wides: Int?
)