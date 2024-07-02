package com.chaudharylabs.cricbuzzclone.data.api.repositories

import android.app.Application
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.TopStoriesResponse
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.story_details.StoryDetailsResponse
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants.API_HOST
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepository(application: Application) : BaseRepository(application) {

    suspend fun getTopStories(
    ): Flow<NetworkResult<TopStoriesResponse>> {
        return flow {
            try {
                emit(NetworkResult.Loading())
                val response =
                    retrofitInterface.getTopStories(API_KEY, API_HOST)
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
                    retrofitInterface.getStoryDetails(API_KEY, API_HOST, "news/v1/detail/$url")
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