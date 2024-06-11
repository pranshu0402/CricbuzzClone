package com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard

data class ScorecardResponse(
    val isMatchComplete: Boolean?,
    val matchHeader: MatchHeader?,
    val responseLastUpdated: Int?,
    val scoreCard: List<ScoreCard>?,
    val status: String?,
    val videos: List<Any>?
)