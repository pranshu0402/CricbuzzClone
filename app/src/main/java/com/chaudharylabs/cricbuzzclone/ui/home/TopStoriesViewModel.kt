package com.chaudharylabs.cricbuzzclone.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.api.repositories.TopStoriesRepository
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.TopStoriesResponse
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.story_details.StoryDetailsResponse
import kotlinx.coroutines.flow.Flow

class TopStoriesViewModel(application: Application) : AndroidViewModel(application) {
    private var topStoriesRepository: TopStoriesRepository = TopStoriesRepository(application)

    suspend fun getTopStories(
    ): Flow<NetworkResult<TopStoriesResponse>> {
        return topStoriesRepository.getTopStories()
    }

    suspend fun getStoryDetails(
        url: String
    ): Flow<NetworkResult<StoryDetailsResponse>> {
        return topStoriesRepository.getStoryDetails(url)
    }
}