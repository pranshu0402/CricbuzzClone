package com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard

data class BowlTeamDetails(
    val bowlTeamId: Int?,
    val bowlTeamName: String?,
    val bowlTeamShortName: String?,
    val bowlersData: BowlersData?
)