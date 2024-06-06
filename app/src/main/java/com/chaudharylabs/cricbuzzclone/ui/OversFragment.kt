package com.chaudharylabs.cricbuzzclone.ui

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
import com.chaudharylabs.cricbuzzclone.data.model.match_details.overs.OversResponse
import com.chaudharylabs.cricbuzzclone.data.model.matches.Matche
import com.chaudharylabs.cricbuzzclone.databinding.FragmentOversBinding
import com.chaudharylabs.cricbuzzclone.ui.adapters.OversAdapter
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants.URL
import com.chaudharylabs.cricbuzzclone.viewmodels.MatchesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class OversFragment : BaseFragment() {

    private lateinit var binding: FragmentOversBinding
    private val viewModel: MatchesViewModel by activityViewModels()
    private var matche: Matche? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overs, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            presenter = this@OversFragment
            lifecycleOwner = this@OversFragment
            executePendingBindings()
        }

        matche = viewModel.match.value
        println(matche?.matchInfo?.matchId)

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getOvers("${matche?.matchInfo?.matchId}", "", "").collect(oversCallback1)
        }
    }

    private val oversCallback1: FlowCollector<NetworkResult<OversResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")

//                        viewModel.getOvers(
//                            "${matche?.matchInfo?.matchId}",
//                            "${it.inningsId}",
//                            "${it.overSummaryList?.size?.minus(1)}"
//                        )
//                            .collect(oversCallback2)

                        lifecycleScope.launch {
                            binding.apply {
                                tvTeam1.text = it.matchHeader?.team1?.shortName
                                tvTeam2.text = it.matchHeader?.team2?.shortName
                                tvStatus.text = it.matchHeader?.status

                                if (!it.matchHeader?.playersOfTheMatch.isNullOrEmpty()) {
                                    val playerOfTheMatchUrl =
                                        "${URL}/c${it.matchHeader?.playersOfTheMatch?.get(0)?.faceImageId.toString()}/.jpg"

                                    tvPom.text =
                                        it.matchHeader?.playersOfTheMatch?.get(0)?.fullName.toString()
                                    Glide.with(ivPlayer).load(playerOfTheMatchUrl).into(ivPlayer)
                                }

                                if (it.matchHeader?.result?.winningteamId == it.matchHeader?.team1?.id) {
                                    tvTeam1.setTextColor(resources.getColor(R.color.white))
                                    tvTeam2.setTextColor(resources.getColor(R.color.primary_soft))
                                    tvTeam1Runs.setTextColor(resources.getColor(R.color.white))
                                    tvTeam2Runs.setTextColor(resources.getColor(R.color.primary_soft))

                                    it.matchScoreDetails?.inningsScoreList.let { scores ->
                                        scores?.forEach { score ->
                                            if (score.batTeamId == it.matchHeader?.team1?.id) {
                                                if (score.wickets == 10) {
                                                    tvTeam1Runs.text =
                                                        "${score.score}(${score.overs})"
                                                } else {
                                                    tvTeam1Runs.text =
                                                        "${score.score}-${score.wickets}(${score.overs})"
                                                }
                                            }

                                            if (score.batTeamId == it.matchHeader?.team2?.id) {
                                                if (score.wickets == 10) {
                                                    tvTeam1Runs.text =
                                                        "${score.score}(${score.overs})"
                                                } else {
                                                    tvTeam1Runs.text =
                                                        "${score.score}-${score.wickets}(${score.overs})"
                                                }
                                            }
                                        }
                                    }
                                }

                                if (it.matchHeader?.result?.winningteamId == it.matchHeader?.team2?.id) {
                                    tvTeam1.setTextColor(resources.getColor(R.color.primary_soft))
                                    tvTeam2.setTextColor(resources.getColor(R.color.white))
                                    tvTeam1Runs.setTextColor(resources.getColor(R.color.primary_soft))
                                    tvTeam2Runs.setTextColor(resources.getColor(R.color.white))
                                }

                                adapter = it.overSummaryList?.let { list ->
                                    OversAdapter(
                                        this@OversFragment,
                                        list
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

    private val oversCallback2: FlowCollector<NetworkResult<OversResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")


                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "response Error :: ${response.message}")
                }
            }

        }

    companion object {
        private const val TAG = "OversFragment"
    }
}