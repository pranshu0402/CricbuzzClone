package com.chaudharylabs.cricbuzzclone.data.api.repositories

import android.app.Application
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.model.match_details.info.MatchDetailsResponse
import com.chaudharylabs.cricbuzzclone.data.model.match_details.live.LiveResponse
import com.chaudharylabs.cricbuzzclone.data.model.match_details.overs.OversResponse
import com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard.ScorecardResponse
import com.chaudharylabs.cricbuzzclone.data.model.match_details.squads.SquadsResponse
import com.chaudharylabs.cricbuzzclone.data.model.matches.MatchesResponse
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants.API_HOST
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MatchesRepository(application: Application) : BaseRepository(application) {

    suspend fun getLiveMatches(
    ): Flow<NetworkResult<MatchesResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                val response =
                    retrofitInterface.getLiveMatches(API_KEY, API_HOST)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRecentMatches(
    ): Flow<NetworkResult<MatchesResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                val response = retrofitInterface.getRecentMatches(API_KEY, API_HOST)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUpcomingMatches(
    ): Flow<NetworkResult<MatchesResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                val response = retrofitInterface.getUpcomingMatches(API_KEY, API_HOST)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMatchInfo(
        matchId: String
    ): Flow<NetworkResult<MatchDetailsResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                val response =
                    retrofitInterface.getMatchInfo(API_KEY, API_HOST, "mcenter/v1/$matchId")
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSquads(
        matchId: String, teamId: String
    ): Flow<NetworkResult<SquadsResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                val response =
                    retrofitInterface.getSquads(
                        API_KEY,
                        API_HOST,
                        "mcenter/v1/$matchId/team/$teamId"
                    )
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getOvers(
        matchId: String
    ): Flow<NetworkResult<OversResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                val response =
                    retrofitInterface.getOvers(API_KEY, API_HOST, "mcenter/v1/$matchId/overs")
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getOversFromTimestamp(
        matchId: String, iId: String, tms: String
    ): Flow<NetworkResult<OversResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                val response =
                    retrofitInterface.getOvers(
                        API_KEY,
                        API_HOST,
                        "mcenter/v1/$matchId/overs?iid=$iId&tms=$tms"
                    )
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getScoreCard(
        matchId: String
    ): Flow<NetworkResult<ScorecardResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                val response =
                    retrofitInterface.getScoreCard(API_KEY, API_HOST, matchId)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCommentaries(
        matchId: String
    ): Flow<NetworkResult<LiveResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                val response =
                    retrofitInterface.getCommentaries(API_KEY, API_HOST, matchId)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}