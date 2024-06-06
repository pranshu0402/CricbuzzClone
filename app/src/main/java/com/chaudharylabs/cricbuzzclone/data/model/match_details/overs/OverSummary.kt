package com.chaudharylabs.cricbuzzclone.data.model.match_details.overs

data class OverSummary(
    val batNonStrikerBalls: Int?,
    val batNonStrikerIds: List<Int?>?,
    val batNonStrikerNames: List<String?>?,
    val batNonStrikerRuns: Int?,
    val batStrikerBalls: Int?,
    val batStrikerIds: List<Int?>?,
    val batStrikerNames: List<String?>?,
    val batStrikerRuns: Int?,
    val batTeamName: String?,
    val bowlIds: List<Int?>?,
    val bowlMaidens: Int?,
    val bowlNames: List<String?>?,
    val bowlOvers: Double?,
    val bowlRuns: Int?,
    val bowlWickets: Int?,
    val event: String?,
    val inningsId: Int?,
    val o_summary: String?,
    val overNum: Double?,
    val runs: Int?,
    val score: Int?,
    val timestamp: Long?,
    val wickets: Int?
)