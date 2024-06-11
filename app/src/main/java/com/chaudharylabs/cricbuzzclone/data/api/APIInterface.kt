package com.chaudharylabs.cricbuzzclone.data.api

import com.chaudharylabs.cricbuzzclone.data.model.match_details.info.MatchDetailsResponse
import com.chaudharylabs.cricbuzzclone.data.model.match_details.overs.OversResponse
import com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard.ScorecardResponse
import com.chaudharylabs.cricbuzzclone.data.model.match_details.squads.SquadsResponse
import com.chaudharylabs.cricbuzzclone.data.model.matches.MatchesResponse
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.TopStoriesResponse
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.story_details.StoryDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Url

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

    @Headers("Content-Type: application/json")
    @GET("")
    suspend fun getStoryDetails(
        @Header("X-RapidAPI-Key") apiKey: String?,
        @Header("X-RapidAPI-Host") apiHost: String?,
        @Url newsId: String?
    ): Response<StoryDetailsResponse?>

    @Headers("Content-Type: application/json")
    @GET("")
    suspend fun getMatchInfo(
        @Header("X-RapidAPI-Key") apiKey: String?,
        @Header("X-RapidAPI-Host") apiHost: String?,
        @Url matchId: String?
    ): Response<MatchDetailsResponse?>

    @Headers("Content-Type: application/json")
    @GET("")
    suspend fun getSquads(
        @Header("X-RapidAPI-Key") apiKey: String?,
        @Header("X-RapidAPI-Host") apiHost: String?,
        @Url squads: String?
    ): Response<SquadsResponse?>

    @Headers("Content-Type: application/json")
    @GET("")
    suspend fun getOvers(
        @Header("X-RapidAPI-Key") apiKey: String?,
        @Header("X-RapidAPI-Host") apiHost: String?,
        @Url overs: String?
    ): Response<OversResponse?>

    @Headers("Content-Type: application/json")
    @GET("mcenter/v1/{matchId}/hscard")
    suspend fun getScoreCard(
        @Header("X-RapidAPI-Key") apiKey: String?,
        @Header("X-RapidAPI-Host") apiHost: String?,
        @Path("matchId") matchId: String?
    ): Response<ScorecardResponse?>
}