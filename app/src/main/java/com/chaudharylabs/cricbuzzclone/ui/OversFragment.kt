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
import com.chaudharylabs.cricbuzzclone.data.model.match_details.overs.OverSummary
import com.chaudharylabs.cricbuzzclone.data.model.match_details.overs.OversResponse
import com.chaudharylabs.cricbuzzclone.data.model.matches.Matche
import com.chaudharylabs.cricbuzzclone.databinding.FragmentOversBinding
import com.chaudharylabs.cricbuzzclone.ui.adapters.OversAdapter
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants.PROFILE_URL
import com.chaudharylabs.cricbuzzclone.viewmodels.MatchesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class OversFragment : BaseFragment() {

    private lateinit var binding: FragmentOversBinding
    private val viewModel: MatchesViewModel by activityViewModels()
    private var matche: Matche? = null
    private var overList: ArrayList<OverSummary> = ArrayList()

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
            adapter = OversAdapter(this@OversFragment)
            executePendingBindings()
        }

        matche = viewModel.match.value

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getOvers(matche?.matchInfo?.matchId.toString()).collect(oversCallback1)
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

                        lifecycleScope.launch {
                            binding.apply {
                                tvTeam1.text = matche?.matchInfo?.team1?.teamSName
                                tvTeam2.text = matche?.matchInfo?.team2?.teamSName
                                tvStatus.text = it.matchHeader?.status

                                if (!it.matchHeader?.playersOfTheMatch.isNullOrEmpty()) {
                                    val playerOfTheMatchUrl =
                                        "${PROFILE_URL}/c${it.matchHeader?.playersOfTheMatch?.get(0)?.faceImageId.toString()}/.jpg"

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

                                it.matchScoreDetails?.inningsScoreList.let { scores ->
                                    scores?.forEach { score ->
                                        if (score.batTeamName == matche?.matchInfo?.team1?.teamSName) {
                                            if (score.wickets == 10) {
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

                                                if (score.overs.toString().contains(".6")) {
                                                    tvTeam1Runs.text =
                                                        "${score.score}-${score.wickets}(${
                                                            score.overs?.toInt()?.plus(1).toString()
                                                                .replace(".6", "")
                                                        })"
                                                } else {
                                                    tvTeam1Runs.text =
                                                        "${score.score}-${score.wickets}(${score.overs})"
                                                }
                                            }
                                        }

                                        if (score.batTeamName == matche?.matchInfo?.team2?.teamSName) {
                                            if (score.wickets == 10) {
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
                                                if (score.overs.toString().contains(".6")) {
                                                    tvTeam2Runs.text =
                                                        "${score.score}-${score.wickets}(${
                                                            score.overs?.toInt()?.plus(1).toString()
                                                                .replace(".6", "")
                                                        })"
                                                } else {
                                                    tvTeam2Runs.text =
                                                        "${score.score}-${score.wickets}(${score.overs})"
                                                }
                                            }
                                        }
                                    }
                                }

                                it.overSummaryList?.let { list ->
                                    overList.addAll(list)
                                    lifecycleScope.launch(Dispatchers.Default) {
                                        getParameters(it.overSummaryList)
                                    }
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

    private fun getParameters(overSummaryList: List<OverSummary>?) {
        overSummaryList?.let { list ->
            if (list.isNotEmpty()) {
                val iId = "${list.size.minus(1).let { it1 -> list[it1].inningsId }}"
                val timeStamp =
                    "${list.size.minus(1).let { it1 -> list[it1].timestamp }}"

                val overNumber = list[list.size.minus(1)].overNum

                if (iId.toInt() == 1 && overNumber == 0.6) {
                    lifecycleScope.launch {
                        binding.adapter?.notifyList(overList)
                        Log.d(TAG, "Overs call complete")
                    }
                } else {
                    getOversFromTimestamp(
                        matche?.matchInfo?.matchId.toString(),
                        iId,
                        timeStamp
                    )
                }
            }
        }
    }

    private fun getOversFromTimestamp(matchId: String, iId: String, timeStamp: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getOversFromTimestamp(matchId, iId, timeStamp)
                .collect(oversCallback2)
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
                        it.overSummaryList?.let { list ->
                            lifecycleScope.launch(Dispatchers.Default) {
                                overList.remove(overList.last())
                                overList.addAll(list)
                                getParameters(it.overSummaryList)
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
        private const val TAG = "OversFragment"
    }
}