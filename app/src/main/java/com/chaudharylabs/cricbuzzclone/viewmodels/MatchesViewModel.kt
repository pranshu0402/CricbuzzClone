package com.chaudharylabs.cricbuzzclone.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.api.repositories.MatchesRepository
import com.chaudharylabs.cricbuzzclone.data.model.Matche
import com.chaudharylabs.cricbuzzclone.data.model.MatchesResponse
import kotlinx.coroutines.flow.Flow

class MatchesViewModel(application: Application) : AndroidViewModel(application) {
    private var matchesRepository: MatchesRepository = MatchesRepository(application)

    var list = MutableLiveData<ArrayList<Matche>>()

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
}