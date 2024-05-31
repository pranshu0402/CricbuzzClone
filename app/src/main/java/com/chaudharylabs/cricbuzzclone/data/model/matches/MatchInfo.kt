package com.chaudharylabs.cricbuzzclone.data.model.matches

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchInfo(
    val currBatTeamId: Int?,
    val endDate: String?,
    val isFantasyEnabled: Boolean?,
    val isTimeAnnounced: Boolean?,
    val matchDesc: String?,
    val matchFormat: String?,
    val matchId: Int?,
    val seriesEndDt: String?,
    val seriesId: Int?,
    val seriesName: String?,
    val seriesStartDt: String?,
    val startDate: String?,
    val state: String?,
    val stateTitle: String?,
    val status: String?,
    val team1: Team1?,
    val team2: Team2?,
    val venueInfo: VenueInfo?
):Parcelable