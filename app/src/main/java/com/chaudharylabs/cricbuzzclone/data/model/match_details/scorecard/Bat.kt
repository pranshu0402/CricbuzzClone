package com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard

data class Bat(
    val balls: Int?,
    val batId: Int?,
    val batName: String?,
    val batShortName: String?,
    val boundaries: Int?,
    val bowlerId: Int?,
    val dots: Int?,
    val fielderId1: Int?,
    val fielderId2: Int?,
    val fielderId3: Int?,
    val fives: Int?,
    val fours: Int?,
    val inMatchChange: String?,
    val isCaptain: Boolean?,
    val isKeeper: Boolean?,
    val isOverseas: Boolean?,
    val mins: Int?,
    val ones: Int?,
    val outDesc: String?,
    val playingXIChange: String?,
    val runs: Int?,
    val sixers: Int?,
    val sixes: Int?,
    val strikeRate: Double?,
    val threes: Int?,
    val twos: Int?,
    val wicketCode: String?
)