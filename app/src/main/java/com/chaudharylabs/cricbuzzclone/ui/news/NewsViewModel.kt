package com.chaudharylabs.cricbuzzclone.ui.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.api.repositories.NewsRepository
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.TopStoriesResponse
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.story_details.StoryDetailsResponse
import kotlinx.coroutines.flow.Flow

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private var newsRepository: NewsRepository = NewsRepository(application)

    suspend fun getTopStories(
    ): Flow<NetworkResult<TopStoriesResponse>> {
        return newsRepository.getTopStories()
    }

    suspend fun getStoryDetails(
        url: String
    ): Flow<NetworkResult<StoryDetailsResponse>> {
        return newsRepository.getStoryDetails(url)
    }
}