package com.chaudharylabs.cricbuzzclone.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.api.repositories.TopStoriesRepository
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.TopStoriesResponse
import kotlinx.coroutines.flow.Flow

class TopStoriesViewModel(application: Application) : AndroidViewModel(application) {
    private var topStoriesRepository: TopStoriesRepository = TopStoriesRepository(application)

    suspend fun getTopStories(
    ): Flow<NetworkResult<TopStoriesResponse>> {
        return topStoriesRepository.getTopStories()
    }
}