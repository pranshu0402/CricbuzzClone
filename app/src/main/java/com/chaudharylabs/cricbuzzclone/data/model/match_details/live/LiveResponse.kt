package com.chaudharylabs.cricbuzzclone.data.model.match_details.live


import com.google.gson.annotations.SerializedName

data class LiveResponse(
    @SerializedName("commentaryList")
    val commentaryList: List<Commentary?>?,
    @SerializedName("commentarySnippetList")
    val commentarySnippetList: List<CommentarySnippet?>?,
    @SerializedName("enableNoContent")
    val enableNoContent: Boolean?,
    @SerializedName("matchHeader")
    val matchHeader: MatchHeader?,
    @SerializedName("matchVideos")
    val matchVideos: List<MatchVideo?>?,
    @SerializedName("miniscore")
    val miniscore: Miniscore?,
    @SerializedName("page")
    val page: String?,
    @SerializedName("responseLastUpdated")
    val responseLastUpdated: Int?
) {
    data class Commentary(
        @SerializedName("ballNbr")
        val ballNbr: Int?,
        @SerializedName("batTeamName")
        val batTeamName: String?,
        @SerializedName("commText")
        val commText: String?,
        @SerializedName("commentaryFormats")
        val commentaryFormats: CommentaryFormats?,
        @SerializedName("event")
        val event: String?,
        @SerializedName("inningsId")
        val inningsId: Int?,
        @SerializedName("overNumber")
        val overNumber: Double?,
        @SerializedName("overSeparator")
        val overSeparator: OverSeparator?,
        @SerializedName("timestamp")
        val timestamp: Long?
    ) {
        data class CommentaryFormats(
            @SerializedName("bold")
            val bold: Bold?
        ) {
            data class Bold(
                @SerializedName("formatId")
                val formatId: List<String?>?,
                @SerializedName("formatValue")
                val formatValue: List<String?>?
            )
        }

        data class OverSeparator(
            @SerializedName("batNonStrikerBalls")
            val batNonStrikerBalls: Int?,
            @SerializedName("batNonStrikerIds")
            val batNonStrikerIds: List<Int?>?,
            @SerializedName("batNonStrikerNames")
            val batNonStrikerNames: List<String?>?,
            @SerializedName("batNonStrikerRuns")
            val batNonStrikerRuns: Int?,
            @SerializedName("batStrikerBalls")
            val batStrikerBalls: Int?,
            @SerializedName("batStrikerIds")
            val batStrikerIds: List<Int?>?,
            @SerializedName("batStrikerNames")
            val batStrikerNames: List<String?>?,
            @SerializedName("batStrikerRuns")
            val batStrikerRuns: Int?,
            @SerializedName("batTeamName")
            val batTeamName: String?,
            @SerializedName("bowlIds")
            val bowlIds: List<Int?>?,
            @SerializedName("bowlMaidens")
            val bowlMaidens: Int?,
            @SerializedName("bowlNames")
            val bowlNames: List<String?>?,
            @SerializedName("bowlOvers")
            val bowlOvers: Double?,
            @SerializedName("bowlRuns")
            val bowlRuns: Int?,
            @SerializedName("bowlWickets")
            val bowlWickets: Int?,
            @SerializedName("event")
            val event: String?,
            @SerializedName("inningsId")
            val inningsId: Int?,
            @SerializedName("o_summary")
            val oSummary: String?,
            @SerializedName("overNum")
            val overNum: Double?,
            @SerializedName("runs")
            val runs: Int?,
            @SerializedName("score")
            val score: Int?,
            @SerializedName("timestamp")
            val timestamp: Long?,
            @SerializedName("wickets")
            val wickets: Int?
        )
    }

    data class CommentarySnippet(
        @SerializedName("commId")
        val commId: Int?,
        @SerializedName("content")
        val content: String?,
        @SerializedName("headline")
        val headline: String?,
        @SerializedName("infraType")
        val infraType: String?,
        @SerializedName("inningsId")
        val inningsId: Int?,
        @SerializedName("isLive")
        val isLive: Boolean?,
        @SerializedName("matchId")
        val matchId: Int?,
        @SerializedName("parsedContent")
        val parsedContent: List<Any?>?,
        @SerializedName("tags")
        val tags: List<Any?>?,
        @SerializedName("timestamp")
        val timestamp: Long?
    )

    data class MatchHeader(
        @SerializedName("alertType")
        val alertType: String?,
        @SerializedName("complete")
        val complete: Boolean?,
        @SerializedName("dayNight")
        val dayNight: Boolean?,
        @SerializedName("domestic")
        val domestic: Boolean?,
        @SerializedName("isMatchNotCovered")
        val isMatchNotCovered: Boolean?,
        @SerializedName("livestreamEnabled")
        val livestreamEnabled: Boolean?,
        @SerializedName("matchCompleteTimestamp")
        val matchCompleteTimestamp: Long?,
        @SerializedName("matchDescription")
        val matchDescription: String?,
        @SerializedName("matchFormat")
        val matchFormat: String?,
        @SerializedName("matchId")
        val matchId: Int?,
        @SerializedName("matchStartTimestamp")
        val matchStartTimestamp: Long?,
        @SerializedName("matchTeamInfo")
        val matchTeamInfo: List<MatchTeamInfo?>?,
        @SerializedName("matchType")
        val matchType: String?,
        @SerializedName("playersOfTheMatch")
        val playersOfTheMatch: List<PlayersOfTheMatch?>?,
        @SerializedName("playersOfTheSeries")
        val playersOfTheSeries: List<Any?>?,
        @SerializedName("result")
        val result: Result?,
        @SerializedName("revisedTarget")
        val revisedTarget: RevisedTarget?,
        @SerializedName("seriesDesc")
        val seriesDesc: String?,
        @SerializedName("seriesId")
        val seriesId: Int?,
        @SerializedName("seriesName")
        val seriesName: String?,
        @SerializedName("state")
        val state: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("team1")
        val team1: Team1?,
        @SerializedName("team2")
        val team2: Team2?,
        @SerializedName("tossResults")
        val tossResults: TossResults?,
        @SerializedName("year")
        val year: Int?
    ) {
        data class MatchTeamInfo(
            @SerializedName("battingTeamId")
            val battingTeamId: Int?,
            @SerializedName("battingTeamShortName")
            val battingTeamShortName: String?,
            @SerializedName("bowlingTeamId")
            val bowlingTeamId: Int?,
            @SerializedName("bowlingTeamShortName")
            val bowlingTeamShortName: String?
        )

        data class PlayersOfTheMatch(
            @SerializedName("captain")
            val captain: Boolean?,
            @SerializedName("faceImageId")
            val faceImageId: Int?,
            @SerializedName("fullName")
            val fullName: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("keeper")
            val keeper: Boolean?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("nickName")
            val nickName: String?,
            @SerializedName("substitute")
            val substitute: Boolean?,
            @SerializedName("teamName")
            val teamName: String?
        )

        data class Result(
            @SerializedName("resultType")
            val resultType: String?,
            @SerializedName("winByInnings")
            val winByInnings: Boolean?,
            @SerializedName("winByRuns")
            val winByRuns: Boolean?,
            @SerializedName("winningMargin")
            val winningMargin: Int?,
            @SerializedName("winningTeam")
            val winningTeam: String?,
            @SerializedName("winningteamId")
            val winningteamId: Int?
        )

        data class RevisedTarget(
            @SerializedName("reason")
            val reason: String?
        )

        data class Team1(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("playerDetails")
            val playerDetails: List<Any?>?,
            @SerializedName("shortName")
            val shortName: String?
        )

        data class Team2(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("playerDetails")
            val playerDetails: List<Any?>?,
            @SerializedName("shortName")
            val shortName: String?
        )

        data class TossResults(
            @SerializedName("decision")
            val decision: String?,
            @SerializedName("tossWinnerId")
            val tossWinnerId: Int?,
            @SerializedName("tossWinnerName")
            val tossWinnerName: String?
        )
    }

    data class MatchVideo(
        @SerializedName("adTag")
        val adTag: String?,
        @SerializedName("appLinkUrl")
        val appLinkUrl: String?,
        @SerializedName("category")
        val category: List<Category?>?,
        @SerializedName("commTimestamp")
        val commTimestamp: String?,
        @SerializedName("headline")
        val headline: String?,
        @SerializedName("imageId")
        val imageId: Int?,
        @SerializedName("infraType")
        val infraType: String?,
        @SerializedName("itemId")
        val itemId: String?,
        @SerializedName("language")
        val language: String?,
        @SerializedName("mappingId")
        val mappingId: String?,
        @SerializedName("planId")
        val planId: Int?,
        @SerializedName("premiumVideoUrl")
        val premiumVideoUrl: String?,
        @SerializedName("tags")
        val tags: List<Tag?>?,
        @SerializedName("videoId")
        val videoId: Int?,
        @SerializedName("videoType")
        val videoType: String?,
        @SerializedName("videoUrl")
        val videoUrl: String?
    ) {
        data class Category(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("imageID")
            val imageID: Int?,
            @SerializedName("name")
            val name: String?
        )

        data class Tag(
            @SerializedName("itemId")
            val itemId: String?,
            @SerializedName("itemName")
            val itemName: String?,
            @SerializedName("itemType")
            val itemType: String?
        )
    }

    data class Miniscore(
        @SerializedName("batTeam")
        val batTeam: BatTeam?,
        @SerializedName("batsmanNonStriker")
        val batsmanNonStriker: BatsmanNonStriker?,
        @SerializedName("batsmanStriker")
        val batsmanStriker: BatsmanStriker?,
        @SerializedName("bowlerNonStriker")
        val bowlerNonStriker: BowlerNonStriker?,
        @SerializedName("bowlerStriker")
        val bowlerStriker: BowlerStriker?,
        @SerializedName("currentRunRate")
        val currentRunRate: Double?,
        @SerializedName("event")
        val event: String?,
        @SerializedName("inningsId")
        val inningsId: Int?,
        @SerializedName("lastWicket")
        val lastWicket: String?,
        @SerializedName("lastWicketScore")
        val lastWicketScore: Int?,
        @SerializedName("latestPerformance")
        val latestPerformance: List<LatestPerformance?>?,
        @SerializedName("matchScoreDetails")
        val matchScoreDetails: MatchScoreDetails?,
        @SerializedName("overSummaryList")
        val overSummaryList: List<Any?>?,
        @SerializedName("overs")
        val overs: Double?,
        @SerializedName("partnerShip")
        val partnerShip: PartnerShip?,
        @SerializedName("ppData")
        val ppData: PpData?,
        @SerializedName("recentOvsStats")
        val recentOvsStats: String?,
        @SerializedName("remRunsToWin")
        val remRunsToWin: Int?,
        @SerializedName("requiredRunRate")
        val requiredRunRate: Double?,
        @SerializedName("responseLastUpdated")
        val responseLastUpdated: Int?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("target")
        val target: Int?
    ) {
        data class BatTeam(
            @SerializedName("teamId")
            val teamId: Int?,
            @SerializedName("teamScore")
            val teamScore: Int?,
            @SerializedName("teamWkts")
            val teamWkts: Int?
        )

        data class BatsmanNonStriker(
            @SerializedName("batBalls")
            val batBalls: Int?,
            @SerializedName("batDots")
            val batDots: Int?,
            @SerializedName("batFours")
            val batFours: Int?,
            @SerializedName("batId")
            val batId: Int?,
            @SerializedName("batMins")
            val batMins: Int?,
            @SerializedName("batName")
            val batName: String?,
            @SerializedName("batRuns")
            val batRuns: Int?,
            @SerializedName("batSixes")
            val batSixes: Int?,
            @SerializedName("batStrikeRate")
            val batStrikeRate: Double?
        )

        data class BatsmanStriker(
            @SerializedName("batBalls")
            val batBalls: Int?,
            @SerializedName("batDots")
            val batDots: Int?,
            @SerializedName("batFours")
            val batFours: Int?,
            @SerializedName("batId")
            val batId: Int?,
            @SerializedName("batMins")
            val batMins: Int?,
            @SerializedName("batName")
            val batName: String?,
            @SerializedName("batRuns")
            val batRuns: Int?,
            @SerializedName("batSixes")
            val batSixes: Int?,
            @SerializedName("batStrikeRate")
            val batStrikeRate: Double?
        )

        data class BowlerNonStriker(
            @SerializedName("bowlEcon")
            val bowlEcon: Double?,
            @SerializedName("bowlId")
            val bowlId: Int?,
            @SerializedName("bowlMaidens")
            val bowlMaidens: Int?,
            @SerializedName("bowlName")
            val bowlName: String?,
            @SerializedName("bowlNoballs")
            val bowlNoballs: Int?,
            @SerializedName("bowlOvs")
            val bowlOvs: Double?,
            @SerializedName("bowlRuns")
            val bowlRuns: Int?,
            @SerializedName("bowlWides")
            val bowlWides: Int?,
            @SerializedName("bowlWkts")
            val bowlWkts: Int?
        )

        data class BowlerStriker(
            @SerializedName("bowlEcon")
            val bowlEcon: Double?,
            @SerializedName("bowlId")
            val bowlId: Int?,
            @SerializedName("bowlMaidens")
            val bowlMaidens: Int?,
            @SerializedName("bowlName")
            val bowlName: String?,
            @SerializedName("bowlNoballs")
            val bowlNoballs: Int?,
            @SerializedName("bowlOvs")
            val bowlOvs: Double?,
            @SerializedName("bowlRuns")
            val bowlRuns: Int?,
            @SerializedName("bowlWides")
            val bowlWides: Int?,
            @SerializedName("bowlWkts")
            val bowlWkts: Int?
        )

        data class LatestPerformance(
            @SerializedName("label")
            val label: String?,
            @SerializedName("runs")
            val runs: Int?,
            @SerializedName("wkts")
            val wkts: Int?
        )

        data class MatchScoreDetails(
            @SerializedName("customStatus")
            val customStatus: String?,
            @SerializedName("highlightedTeamId")
            val highlightedTeamId: Int?,
            @SerializedName("inningsScoreList")
            val inningsScoreList: List<InningsScore?>?,
            @SerializedName("isMatchNotCovered")
            val isMatchNotCovered: Boolean?,
            @SerializedName("matchFormat")
            val matchFormat: String?,
            @SerializedName("matchId")
            val matchId: Int?,
            @SerializedName("matchTeamInfo")
            val matchTeamInfo: List<MatchTeamInfo?>?,
            @SerializedName("state")
            val state: String?,
            @SerializedName("tossResults")
            val tossResults: TossResults?
        ) {
            data class InningsScore(
                @SerializedName("ballNbr")
                val ballNbr: Int?,
                @SerializedName("batTeamId")
                val batTeamId: Int?,
                @SerializedName("batTeamName")
                val batTeamName: String?,
                @SerializedName("inningsId")
                val inningsId: Int?,
                @SerializedName("isDeclared")
                val isDeclared: Boolean?,
                @SerializedName("isFollowOn")
                val isFollowOn: Boolean?,
                @SerializedName("overs")
                val overs: Double?,
                @SerializedName("score")
                val score: Int?,
                @SerializedName("wickets")
                val wickets: Int?
            )

            data class MatchTeamInfo(
                @SerializedName("battingTeamId")
                val battingTeamId: Int?,
                @SerializedName("battingTeamShortName")
                val battingTeamShortName: String?,
                @SerializedName("bowlingTeamId")
                val bowlingTeamId: Int?,
                @SerializedName("bowlingTeamShortName")
                val bowlingTeamShortName: String?
            )

            data class TossResults(
                @SerializedName("decision")
                val decision: String?,
                @SerializedName("tossWinnerId")
                val tossWinnerId: Int?,
                @SerializedName("tossWinnerName")
                val tossWinnerName: String?
            )
        }

        data class PartnerShip(
            @SerializedName("balls")
            val balls: Int?,
            @SerializedName("runs")
            val runs: Int?
        )

        data class PpData(
            @SerializedName("pp_1")
            val pp1: Pp1?
        ) {
            data class Pp1(
                @SerializedName("ppId")
                val ppId: Int?,
                @SerializedName("ppOversFrom")
                val ppOversFrom: Double?,
                @SerializedName("ppOversTo")
                val ppOversTo: Double?,
                @SerializedName("ppType")
                val ppType: String?,
                @SerializedName("runsScored")
                val runsScored: Int?
            )
        }
    }
}