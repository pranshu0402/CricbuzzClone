package com.chaudharylabs.cricbuzzclone.data.api

import com.chaudharylabs.cricbuzzclone.data.model.matches.MatchesResponse
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.TopStoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("matches/v1/live")
    suspend fun getLiveMatches(
        @Header("X-RapidAPI-Key") apiKey: String?,
        @Header("X-RapidAPI-Host") apiHost: String?
    ): Response<MatchesResponse?>

    @Headers("Content-Type: application/json")
    @GET("matches/v1/recent")
    suspend fun getRecentMatches(
        @Header("X-RapidAPI-Key") apiKey: String?,
        @Header("X-RapidAPI-Host") apiHost: String?
    ): Response<MatchesResponse?>

    @Headers("Content-Type: application/json")
    @GET("matches/v1/upcoming")
    suspend fun getUpcomingMatches(
        @Header("X-RapidAPI-Key") apiKey: String?,
        @Header("X-RapidAPI-Host") apiHost: String?
    ): Response<MatchesResponse?>

    @Headers("Content-Type: application/json")
    @GET("news/v1/index")
    suspend fun getTopStories(
        @Header("X-RapidAPI-Key") apiKey: String?,
        @Header("X-RapidAPI-Host") apiHost: String?
    ): Response<TopStoriesResponse?>
}