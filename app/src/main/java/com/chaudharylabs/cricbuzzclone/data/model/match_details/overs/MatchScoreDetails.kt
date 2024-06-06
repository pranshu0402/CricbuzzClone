package com.chaudharylabs.cricbuzzclone.data.model.match_details.overs

data class MatchScoreDetails(
    val customStatus: String?,
    val highlightedTeamId: Int?,
    val inningsScoreList: List<InningsScore>?,
    val isMatchNotCovered: Boolean?,
    val matchFormat: String?,
    val matchId: Int?,
    val matchTeamInfo: List<MatchTeamInfo>?,
    val state: String?,
    val tossResults: TossResults?
)