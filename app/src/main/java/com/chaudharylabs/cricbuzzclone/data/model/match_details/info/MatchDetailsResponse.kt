package com.chaudharylabs.cricbuzzclone.data.model.match_details.info

data class MatchDetailsResponse(
    val broadcastInfo: List<BroadcastInfo>?,
    val matchInfo: MatchInfo?,
    val venueInfo: VenueInfo?
)