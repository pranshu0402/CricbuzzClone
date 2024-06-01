package com.chaudharylabs.cricbuzzclone.data.api.repositories

import android.app.Application
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.model.match_details.MatchDetailsResponse
import com.chaudharylabs.cricbuzzclone.data.model.matches.MatchesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MatchesRepository(application: Application) : BaseRepository(application) {

    private val apiKey = "f80e239580msh700af856dd70624p1ba082jsn44ef85800156"
    private val apiHost = "cricbuzz-cricket.p.rapidapi.com"

    suspend fun getLiveMatches(
    ): Flow<NetworkResult<MatchesResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                val response = retrofitInterface.getLiveMatches(apiKey, apiHost)
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
                val response = retrofitInterface.getRecentMatches(apiKey, apiHost)
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
                val response = retrofitInterface.getUpcomingMatches(apiKey, apiHost)
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
                    retrofitInterface.getMatchInfo(apiKey, apiHost, "mcenter/v1/$matchId")
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