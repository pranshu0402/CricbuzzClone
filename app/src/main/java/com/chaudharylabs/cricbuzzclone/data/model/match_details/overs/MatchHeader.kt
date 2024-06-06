package com.chaudharylabs.cricbuzzclone.data.model.match_details.overs

import com.chaudharylabs.cricbuzzclone.data.model.match_details.info.Team2

data class MatchHeader(
    val alertType: String?,
    val complete: Boolean?,
    val dayNight: Boolean?,
    val domestic: Boolean?,
    val isMatchNotCovered: Boolean?,
    val livestreamEnabled: Boolean?,
    val matchCompleteTimestamp: Long?,
    val matchDescription: String?,
    val matchFormat: String?,
    val matchId: Int?,
    val matchStartTimestamp: Long?,
    val matchTeamInfo: List<MatchTeamInfo?>?,
    val matchType: String?,
    val playersOfTheMatch: List<PlayersOfTheMatch?>?,
    val playersOfTheSeries: List<Any?>?,
    val result: Result?,
    val revisedTarget: RevisedTarget?,
    val seriesDesc: String?,
    val seriesId: Int?,
    val seriesName: String?,
    val state: String?,
    val status: String?,
    val team1: Team1?,
    val team2: Team2?,
    val tossResults: TossResults?,
    val year: Int?
)