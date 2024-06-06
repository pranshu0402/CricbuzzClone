package com.chaudharylabs.cricbuzzclone.data.model.match_details.overs

data class OversResponse(
    val batTeam: BatTeam?,
    val batsmanNonStriker: BatsmanNonStriker?,
    val batsmanStriker: BatsmanStriker?,
    val bowlerNonStriker: BowlerNonStriker?,
    val bowlerStriker: BowlerStriker?,
    val currentRunRate: Double?,
    val event: String?,
    val inningsId: Int?,
    val lastWicket: String?,
    val lastWicketScore: Int?,
    val latestPerformance: List<LatestPerformance>?,
    val matchHeader: MatchHeader?,
    val matchScoreDetails: MatchScoreDetails?,
    val matchUdrs: MatchUdrs?,
    val overSummaryList: List<OverSummary>?,
    val overs: Double?,
    val partnerShip: PartnerShip?,
    val ppData: PpData?,
    val recentOvsStats: String?,
    val remRunsToWin: Int?,
    val requiredRunRate: Double?,
    val responseLastUpdated: Int?,
    val status: String?
)