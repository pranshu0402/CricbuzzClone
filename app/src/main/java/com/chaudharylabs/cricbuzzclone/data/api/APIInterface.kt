package com.chaudharylabs.cricbuzzclone.data.api

import com.chaudharylabs.cricbuzzclone.data.model.MatchesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("recent")
    suspend fun getMatches(
        @Header("X-RapidAPI-Key") apiKey: String?,
        @Header("X-RapidAPI-Host") apiHost: String?
    ): Response<MatchesResponse?>
}