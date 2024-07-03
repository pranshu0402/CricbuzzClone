package com.chaudharylabs.cricbuzzclone.ui.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.api.repositories.NewsRepository
import com.chaudharylabs.cricbuzzclone.data.model.news.categories.CategoryResponse
import com.chaudharylabs.cricbuzzclone.data.model.news.topics.TopicsResponse
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.TopStoriesResponse
import kotlinx.coroutines.flow.Flow

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private var newsRepository: NewsRepository = NewsRepository(application)

    suspend fun getAllStories(
    ): Flow<NetworkResult<TopStoriesResponse>> {
        return newsRepository.getAllStories()
    }

    suspend fun getPremiumStories(
    ): Flow<NetworkResult<TopStoriesResponse>> {
        return newsRepository.getPremiumStories()
    }

    suspend fun getCategories(
    ): Flow<NetworkResult<CategoryResponse>> {
        return newsRepository.getCategories()
    }

    suspend fun getTopics(
    ): Flow<NetworkResult<TopicsResponse>> {
        return newsRepository.getTopics()
    }

    suspend fun getListByCategory(
        id: String
    ): Flow<NetworkResult<TopStoriesResponse>> {
        return newsRepository.getListByCategory(id)
    }

    suspend fun getListByTopic(
        id: String
    ): Flow<NetworkResult<TopStoriesResponse>> {
        return newsRepository.getListByTopic(id)
    }
}