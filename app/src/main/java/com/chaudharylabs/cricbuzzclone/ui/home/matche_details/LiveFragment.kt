package com.chaudharylabs.cricbuzzclone.ui.home.matche_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.model.match_details.live.LiveResponse
import com.chaudharylabs.cricbuzzclone.data.model.matches.Matche
import com.chaudharylabs.cricbuzzclone.databinding.FragmentLiveBinding
import com.chaudharylabs.cricbuzzclone.ui.BaseFragment
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.adapter.LiveAdapter
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.TimeSource

class LiveFragment : BaseFragment() {

    private lateinit var binding: FragmentLiveBinding
    private val viewModel: MatchesViewModel by activityViewModels()
    private var matche: Matche? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_live, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            presenter = this@LiveFragment
            lifecycleOwner = this@LiveFragment
            executePendingBindings()
        }

        setBottomNavVisibility(View.GONE)

        matche = viewModel.match.value
        println(matche?.matchInfo?.matchId)

        lifecycleScope.launch {
            viewModel.getCommentaries(matche?.matchInfo?.matchId.toString()).collect(liveCallback)
        }

        if (matche?.matchInfo?.state == "Upcoming") {
            viewModel.endTime.observe(viewLifecycleOwner) { endTime ->
                if (endTime.isNotEmpty()) {
                    binding.tvUpcomingRemainingTime.text = endTime
                }
            }
        }
    }

    private val liveCallback: FlowCollector<NetworkResult<LiveResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")

                        binding.apply {

                            when (matche?.matchInfo?.state) {
                                "Complete" -> {
                                    lytLive.visibility = View.GONE
                                    lytRecent.visibility = View.VISIBLE
                                    lytUpcoming.visibility = View.GONE

                                    if (it.matchHeader?.result?.winningteamId == it.matchHeader?.team1?.id) {
                                        tvRecentTeam1.setTextColor(resources.getColor(R.color.white))
                                        tvRecentTeam2.setTextColor(resources.getColor(R.color.primary_soft))
                                        tvTeam1Runs.setTextColor(resources.getColor(R.color.white))
                                        tvTeam2Runs.setTextColor(resources.getColor(R.color.primary_soft))
                                    } else {
                                        tvRecentTeam1.setTextColor(resources.getColor(R.color.primary_soft))
                                        tvRecentTeam2.setTextColor(resources.getColor(R.color.white))
                                        tvTeam1Runs.setTextColor(resources.getColor(R.color.primary_soft))
                                        tvTeam2Runs.setTextColor(resources.getColor(R.color.white))
                                    }

                                    if (it.matchHeader?.matchFormat == "TEST") {

                                        val team1Inning1Wickets =
                                            if (matche?.matchScore?.team1Score?.inngs1?.wickets == 10) {
                                                ""
                                            } else {
                                                "-${matche?.matchScore?.team1Score?.inngs1?.wickets}"
                                            }
                                        val team1Inning2Wickets =
                                            if (matche?.matchScore?.team1Score?.inngs2?.wickets == 10) {
                                                ""
                                            } else {
                                                "-${matche?.matchScore?.team1Score?.inngs2?.wickets}"
                                            }

                                        val team2Inning1Wickets =
                                            if (matche?.matchScore?.team2Score?.inngs1?.wickets == 10) {
                                                ""
                                            } else {
                                                "-${matche?.matchScore?.team2Score?.inngs1?.wickets}"
                                            }

                                        val team2Inning2Wickets =
                                            if (matche?.matchScore?.team2Score?.inngs2?.wickets == 10) {
                                                ""
                                            } else {
                                                "-${matche?.matchScore?.team2Score?.inngs2?.wickets}"
                                            }

                                        val runs1 =
                                            "${matche?.matchScore?.team1Score?.inngs1?.runs}$team1Inning1Wickets & ${matche?.matchScore?.team1Score?.inngs2?.runs}$team1Inning2Wickets"

                                        val runs2 =
                                            "${matche?.matchScore?.team2Score?.inngs1?.runs}$team2Inning1Wickets & ${matche?.matchScore?.team2Score?.inngs2?.runs}$team2Inning2Wickets"


                                        tvTeam1Runs.text = runs1
                                        tvTeam2Runs.text = runs2

                                    } else {
                                        tvTeam1Runs.text =
                                            "${matche?.matchScore?.team1Score?.inngs1?.runs}-${matche?.matchScore?.team1Score?.inngs1?.wickets} (${matche?.matchScore?.team1Score?.inngs1?.overs})"
                                        tvTeam2Runs.text =
                                            "${matche?.matchScore?.team2Score?.inngs1?.runs}-${matche?.matchScore?.team2Score?.inngs1?.wickets} (${matche?.matchScore?.team2Score?.inngs1?.overs})"
                                    }

                                    if (it.miniscore?.batTeam?.teamId == matche?.matchInfo?.team1?.teamId) {
                                        tvLiveTeamName.text = it.matchHeader?.team1?.shortName
                                    } else {
                                        tvLiveTeamName.text = it.matchHeader?.team2?.shortName
                                    }

                                    tvRecentStatus.text = it.matchHeader?.status
                                }

                                "Upcoming" -> {
                                    lytLive.visibility = View.GONE
                                    lytRecent.visibility = View.GONE
                                    lytUpcoming.visibility = View.VISIBLE

                                    tvUpcomingMatchStartTime.text =
                                        getDateFromMilliseconds(it.matchHeader?.matchStartTimestamp)
                                    it.matchHeader?.matchStartTimestamp?.let { time ->
                                        viewModel.showTimer(
                                            time
                                        )
                                    }
                                    tvUpcomingStatus.text = it.matchHeader?.status

                                }

                                else -> {
                                    lytLive.visibility = View.VISIBLE
                                    lytRecent.visibility = View.GONE
                                    lytUpcoming.visibility = View.GONE

                                    tvLiveRuns.text =
                                        "${it.miniscore?.batTeam?.teamScore}-${it.miniscore?.batTeam?.teamWkts}"
                                    tvLiveOvers.text = "(${it.miniscore?.overs})"
                                    tvLiveCrrValue.text = it.miniscore?.currentRunRate.toString()
                                    tvLiveStatus.text = it.matchHeader?.status
                                    tvLiveTeamName.text = it.commentaryList?.get(0)?.batTeamName

                                    if (it.miniscore?.requiredRunRate?.toInt() != 0) {
                                        tvLiveReqRateValue.text =
                                            it.miniscore?.requiredRunRate.toString()
                                        tvLiveReqRateValue.visibility = View.VISIBLE
                                        tvLiveReqRate.visibility = View.VISIBLE
                                    }
                                }
                            }

                            if (!it.matchHeader?.playersOfTheMatch.isNullOrEmpty()) {
                                lytPom.visibility = View.VISIBLE
                                val playerOfTheMatchUrl =
                                    "${Constants.PROFILE_URL}/c${
                                        it.matchHeader?.playersOfTheMatch?.get(
                                            0
                                        )?.faceImageId.toString()
                                    }/.jpg"

                                tvMom.text =
                                    it.matchHeader?.playersOfTheMatch?.get(0)?.fullName.toString()
                                Glide.with(ivPlayer).load(playerOfTheMatchUrl).into(ivPlayer)
                            }

                            if (!it.matchHeader?.playersOfTheSeries.isNullOrEmpty()) {
                                lytPos.visibility = View.VISIBLE
                                val playerOfTheSeriesUrl =
                                    "${Constants.PROFILE_URL}/c${
                                        it.matchHeader?.playersOfTheSeries?.get(
                                            0
                                        )?.faceImageId.toString()
                                    }/.jpg"

                                tvMos.text =
                                    it.matchHeader?.playersOfTheSeries?.get(0)?.fullName.toString()
                                Glide.with(ivPlayers).load(playerOfTheSeriesUrl).into(ivPlayers)
                            }

//                            it.miniscore?.matchScoreDetails?.inningsScoreList.let { scores ->
//                                scores?.forEach { score ->
//                                    if (score?.batTeamName == matche?.matchInfo?.team1?.teamSName) {
//                                        if (score?.wickets == 10) {
//                                            if (score.overs.toString().contains(".6")) {
//                                                tvTeam1Runs.text =
//                                                    "${score.score}(${
//                                                        score.overs?.toInt()?.plus(1).toString()
//                                                            .replace(".6", "")
//                                                    })"
//                                            } else {
//                                                tvTeam1Runs.text =
//                                                    "${score.score}(${score.overs})"
//                                            }
//                                        } else {
//
//                                            if (score?.overs.toString().contains(".6")) {
//                                                tvTeam1Runs.text =
//                                                    "${score?.score}-${score?.wickets}(${
//                                                        score?.overs?.toInt()?.plus(1).toString()
//                                                            .replace(".6", "")
//                                                    })"
//                                            } else {
//                                                tvTeam1Runs.text =
//                                                    "${score?.score}-${score?.wickets}(${score?.overs})"
//                                            }
//                                        }
//                                    }
//
//                                    if (score?.batTeamName == matche?.matchInfo?.team2?.teamSName) {
//                                        if (score?.wickets == 10) {
//                                            if (score.overs.toString().contains(".6")) {
//                                                tvTeam2Runs.text =
//                                                    "${score.score}(${
//                                                        score.overs?.toInt()?.plus(1).toString()
//                                                            .replace(".6", "")
//                                                    })"
//                                            } else {
//                                                tvTeam2Runs.text =
//                                                    "${score.score}(${score.overs})"
//                                            }
//                                        } else {
//                                            if (score?.overs.toString().contains(".6")) {
//                                                tvTeam2Runs.text =
//                                                    "${score?.score}-${score?.wickets}(${
//                                                        score?.overs?.toInt()?.plus(1).toString()
//                                                            .replace(".6", "")
//                                                    })"
//                                            } else {
//                                                tvTeam2Runs.text =
//                                                    "${score?.score}-${score?.wickets}(${score?.overs})"
//                                            }
//                                        }
//                                    }
//                                }
//                            }

                            it.commentaryList?.let { commentaryList ->
                                adapter = it.commentarySnippetList?.let { commentarySnippetList ->
                                    LiveAdapter(
                                        this@LiveFragment,
                                        commentaryList,
                                        commentarySnippetList
                                    )
                                }
                            }

                        }
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "response Error :: ${response.message}")
                }
            }

        }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopTimer()
    }

    companion object {
        private const val TAG = "LiveFragment"
    }
}