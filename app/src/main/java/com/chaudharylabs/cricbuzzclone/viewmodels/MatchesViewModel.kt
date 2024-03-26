package com.chaudharylabs.cricbuzzclone.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.api.repositories.MatchesRepository
import com.chaudharylabs.cricbuzzclone.data.model.MatchesResponse
import kotlinx.coroutines.flow.Flow

class MatchesViewModel(application: Application) : AndroidViewModel(application) {
    private var matchesRepository: MatchesRepository = MatchesRepository(application)

    suspend fun getMatches(
        apiKey: String,
        apiHost: String
    ): Flow<NetworkResult<MatchesResponse>> {
        return matchesRepository.getMatches(apiKey, apiHost)
    }
}