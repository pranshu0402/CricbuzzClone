package com.chaudharylabs.cricbuzzclone.data.model.match_details

data class MatchDetailsResponse(
    val broadcastInfo: List<BroadcastInfo>?,
    val matchInfo: MatchInfo?,
    val venueInfo: VenueInfo?
)