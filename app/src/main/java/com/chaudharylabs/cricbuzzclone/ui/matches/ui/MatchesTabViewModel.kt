package com.chaudharylabs.cricbuzzclone.ui.matches.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.api.repositories.MatchesRepository
import com.chaudharylabs.cricbuzzclone.data.model.match_details.info.MatchDetailsResponse
import com.chaudharylabs.cricbuzzclone.data.model.match_details.live.LiveResponse
import com.chaudharylabs.cricbuzzclone.data.model.match_details.overs.OversResponse
import com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard.ScorecardResponse
import com.chaudharylabs.cricbuzzclone.data.model.match_details.squads.SquadsResponse
import com.chaudharylabs.cricbuzzclone.data.model.matches.Matche
import com.chaudharylabs.cricbuzzclone.data.model.matches.MatchesResponse
import kotlinx.coroutines.flow.Flow

class MatchesTabViewModel(application: Application) : AndroidViewModel(application) {
    private var matchesRepository: MatchesRepository = MatchesRepository(application)

    var list = MutableLiveData<ArrayList<Matche>>()
    var match = MutableLiveData<Matche>()

    suspend fun getLiveMatches(
    ): Flow<NetworkResult<MatchesResponse>> {
        return matchesRepository.getLiveMatches()
    }

    suspend fun getRecentMatches(
    ): Flow<NetworkResult<MatchesResponse>> {
        return matchesRepository.getRecentMatches()
    }

    suspend fun getUpcomingMatches(
    ): Flow<NetworkResult<MatchesResponse>> {
        return matchesRepository.getUpcomingMatches()
    }

    suspend fun getMatchInfo(matchId: String): Flow<NetworkResult<MatchDetailsResponse>> {
        return matchesRepository.getMatchInfo(matchId)
    }

    suspend fun getSquads(
        matchId: String,
        teamId: String
    ): Flow<NetworkResult<SquadsResponse>> {
        return matchesRepository.getSquads(matchId, teamId)
    }

    suspend fun getOvers(
        matchId: String
    ): Flow<NetworkResult<OversResponse>> {
        return matchesRepository.getOvers(matchId)
    }

    suspend fun getOversFromTimestamp(
        matchId: String,
        iId: String,
        tms: String
    ): Flow<NetworkResult<OversResponse>> {
        return matchesRepository.getOversFromTimestamp(matchId, iId, tms)
    }

    suspend fun getScoreCard(
        matchId: String
    ): Flow<NetworkResult<ScorecardResponse>> {
        return matchesRepository.getScoreCard(matchId)
    }

    suspend fun getCommentaries(
        matchId: String
    ): Flow<NetworkResult<LiveResponse>> {
        return matchesRepository.getCommentaries(matchId)
    }
}