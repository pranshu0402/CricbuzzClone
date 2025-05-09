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

                            tvTeam1.text = matche?.matchInfo?.team1?.teamSName
                            tvTeam2.text = matche?.matchInfo?.team2?.teamSName
                            tvStatus.text = it.matchHeader?.status

                            if (!it.matchHeader?.playersOfTheMatch.isNullOrEmpty()) {
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

                            if (it.matchHeader?.result?.winningteamId == it.matchHeader?.team1?.id) {
                                tvTeam1.setTextColor(resources.getColor(R.color.white))
                                tvTeam2.setTextColor(resources.getColor(R.color.primary_soft))
                                tvTeam1Runs.setTextColor(resources.getColor(R.color.white))
                                tvTeam2Runs.setTextColor(resources.getColor(R.color.primary_soft))
                            }

                            if (it.matchHeader?.result?.winningteamId == it.matchHeader?.team2?.id) {
                                tvTeam1.setTextColor(resources.getColor(R.color.primary_soft))
                                tvTeam2.setTextColor(resources.getColor(R.color.white))
                                tvTeam1Runs.setTextColor(resources.getColor(R.color.primary_soft))
                                tvTeam2Runs.setTextColor(resources.getColor(R.color.white))
                            }

                            it.miniscore?.matchScoreDetails?.inningsScoreList.let { scores ->
                                scores?.forEach { score ->
                                    if (score?.batTeamName == matche?.matchInfo?.team1?.teamSName) {
                                        if (score?.wickets == 10) {
                                            if (score.overs.toString().contains(".6")) {
                                                tvTeam1Runs.text =
                                                    "${score.score}(${
                                                        score.overs?.toInt()?.plus(1).toString()
                                                            .replace(".6", "")
                                                    })"
                                            } else {
                                                tvTeam1Runs.text =
                                                    "${score.score}(${score.overs})"
                                            }
                                        } else {

                                            if (score?.overs.toString().contains(".6")) {
                                                tvTeam1Runs.text =
                                                    "${score?.score}-${score?.wickets}(${
                                                        score?.overs?.toInt()?.plus(1).toString()
                                                            .replace(".6", "")
                                                    })"
                                            } else {
                                                tvTeam1Runs.text =
                                                    "${score?.score}-${score?.wickets}(${score?.overs})"
                                            }
                                        }
                                    }

                                    if (score?.batTeamName == matche?.matchInfo?.team2?.teamSName) {
                                        if (score?.wickets == 10) {
                                            if (score.overs.toString().contains(".6")) {
                                                tvTeam2Runs.text =
                                                    "${score.score}(${
                                                        score.overs?.toInt()?.plus(1).toString()
                                                            .replace(".6", "")
                                                    })"
                                            } else {
                                                tvTeam2Runs.text =
                                                    "${score.score}(${score.overs})"
                                            }
                                        } else {
                                            if (score?.overs.toString().contains(".6")) {
                                                tvTeam2Runs.text =
                                                    "${score?.score}-${score?.wickets}(${
                                                        score?.overs?.toInt()?.plus(1).toString()
                                                            .replace(".6", "")
                                                    })"
                                            } else {
                                                tvTeam2Runs.text =
                                                    "${score?.score}-${score?.wickets}(${score?.overs})"
                                            }
                                        }
                                    }
                                }
                            }

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

    companion object {
        private const val TAG = "LiveFragment"
    }
}