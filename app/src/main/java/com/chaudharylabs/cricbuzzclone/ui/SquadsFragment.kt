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
import com.chaudharylabs.cricbuzzclone.data.model.match_details.squads.SquadsResponse
import com.chaudharylabs.cricbuzzclone.data.model.matches.Matche
import com.chaudharylabs.cricbuzzclone.databinding.FragmentSquadsBinding
import com.chaudharylabs.cricbuzzclone.ui.adapters.Bench1Adapter
import com.chaudharylabs.cricbuzzclone.ui.adapters.Bench2Adapter
import com.chaudharylabs.cricbuzzclone.ui.adapters.Staff1Adapter
import com.chaudharylabs.cricbuzzclone.ui.adapters.Staff2Adapter
import com.chaudharylabs.cricbuzzclone.ui.adapters.Team1Adapter
import com.chaudharylabs.cricbuzzclone.ui.adapters.Team2Adapter
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants.URL
import com.chaudharylabs.cricbuzzclone.viewmodels.MatchesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class SquadsFragment : BaseFragment() {

    private lateinit var binding: FragmentSquadsBinding
    private val viewModel: MatchesViewModel by activityViewModels()
    private var matche: Matche? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_squads, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            presenter = this@SquadsFragment
            lifecycleOwner = this@SquadsFragment
            executePendingBindings()
        }

        setBottomNavVisibility(View.GONE)

        matche = viewModel.match.value
        println(matche?.matchInfo?.matchId)
        println("${matche?.matchInfo?.team1?.teamId.toString()},${matche?.matchInfo?.team2?.teamId.toString()}")

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getSquads(
                matche?.matchInfo?.matchId.toString(),
                matche?.matchInfo?.team1?.teamId.toString()
            ).collect(squadsCallback1)
        }
    }

    private val squadsCallback1: FlowCollector<NetworkResult<SquadsResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")

                        viewModel.getSquads(
                            matche?.matchInfo?.matchId.toString(),
                            matche?.matchInfo?.team2?.teamId.toString()
                        ).collect(squadsCallback2)

                        lifecycleScope.launch {

                            it.players?.bench?.filter { it?.isSupportStaff == false }

                            binding.apply {

                                tvTeam1.text = matche?.matchInfo?.team1?.teamSName
                                tvTeam2.text = matche?.matchInfo?.team2?.teamSName

                                Glide.with(ivTeam1.context)
                                    .load("${URL}/c${matche?.matchInfo?.team1?.imageId.toString()}/.jpg")
                                    .into(ivTeam1)
                                Glide.with(ivTeam2.context)
                                    .load("$URL/c${matche?.matchInfo?.team2?.imageId.toString()}/.jpg")
                                    .into(ivTeam2)

                                if (!it.players?.playingXI.isNullOrEmpty() &&
                                    !it.players?.bench.isNullOrEmpty()
                                ) {
                                    binding.apply {
                                        rvTeam1.adapter =
                                            Team1Adapter(
                                                this@SquadsFragment,
                                                it.players?.playingXI!!
                                            )
                                        rvBench1.adapter =
                                            Bench1Adapter(this@SquadsFragment, it.players.bench!!)
                                        rvStaff1.adapter =
                                            Staff1Adapter(
                                                this@SquadsFragment,
                                                it.players.bench.filter { it?.isSupportStaff == true }
                                            )
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

    private val squadsCallback2: FlowCollector<NetworkResult<SquadsResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")

                        lifecycleScope.launch {

                            it.players?.bench?.filter { it?.isSupportStaff == false }

                            if (!it.players?.playingXI.isNullOrEmpty() &&
                                !it.players?.bench.isNullOrEmpty()
                            ) {
                                binding.apply {
                                    rvTeam2.adapter =
                                        Team2Adapter(
                                            this@SquadsFragment,
                                            it.players?.playingXI!!
                                        )
                                    rvBench2.adapter =
                                        Bench2Adapter(this@SquadsFragment, it.players.bench!!)
                                    rvStaff2.adapter =
                                        Staff2Adapter(
                                            this@SquadsFragment,
                                            it.players.bench.filter { it?.isSupportStaff == true }
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
        private const val TAG = "SquadsFragment"
    }
}