package com.chaudharylabs.cricbuzzclone.data.api.repositories

import android.app.Application
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.TopStoriesResponse
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.story_details.StoryDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TopStoriesRepository(application: Application) : BaseRepository(application) {

    private val apiKey = "f80e239580msh700af856dd70624p1ba082jsn44ef85800156"
    private val apiHost = "cricbuzz-cricket.p.rapidapi.com"

    suspend fun getTopStories(
    ): Flow<NetworkResult<TopStoriesResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                val response = retrofitInterface.getTopStories(apiKey, apiHost)
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

    suspend fun getStoryDetails(
        url: String
    ): Flow<NetworkResult<StoryDetailsResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                val response =
                    retrofitInterface.getStoryDetails(apiKey, apiHost, "news/v1/detail/$url")
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