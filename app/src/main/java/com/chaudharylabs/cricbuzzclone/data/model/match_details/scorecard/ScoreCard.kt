package com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard

data class ScoreCard(
    val batTeamDetails: BatTeamDetails?,
    val bowlTeamDetails: BowlTeamDetails?,
    val extrasData: ExtrasData?,
    val inningsId: Int?,
    val matchId: Int?,
    val partnershipsData: PartnershipsData?,
    val scoreDetails: ScoreDetails?,
    val timeScore: Long?,
    val wicketsData: WicketsData?
)