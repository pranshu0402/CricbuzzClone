package com.chaudharylabs.cricbuzzclone.ui.home.matche_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard.Bat
import com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard.Bowl
import com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard.Partnerships
import com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard.ScorecardResponse
import com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard.Wkt
import com.chaudharylabs.cricbuzzclone.data.model.matches.Matche
import com.chaudharylabs.cricbuzzclone.databinding.FragmentScorecardBinding
import com.chaudharylabs.cricbuzzclone.ui.BaseFragment
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.adapter.BatterScorecardAdapter
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.adapter.BowlerScorecardAdapter
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.adapter.FallOfWicketsScorecardAdapter
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.adapter.PartnershipScorecardAdapter
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class ScorecardFragment : BaseFragment() {

    private lateinit var binding: FragmentScorecardBinding
    private val viewModel: MatchesViewModel by activityViewModels()
    private var match: Matche? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scorecard, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            presenter = this@ScorecardFragment
            lifecycleOwner = this@ScorecardFragment
            executePendingBindings()
        }

        setBottomNavVisibility(View.GONE)

        match = viewModel.match.value
        println(match?.matchInfo?.matchId)

        lifecycleScope.launch {
            viewModel.getScoreCard(match?.matchInfo?.matchId.toString()).collect(scorecardCallback)
        }
    }

    private val scorecardCallback: FlowCollector<NetworkResult<ScorecardResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")

                        binding.apply {

                            tvStatus.text = it.matchHeader?.status

                            it.scoreCard.let { scorecard ->

                                tvTeam1Name.text =
                                    scorecard?.get(0)?.batTeamDetails?.batTeamName.toString()

                                tvTeam2Name.text =
                                    scorecard?.get(1)?.batTeamDetails?.batTeamName.toString()

                                tvTeam1Runs.text = scorecard?.get(0)?.scoreDetails?.runs.toString()
                                tvTeam1Wickets.text =
                                    "-${scorecard?.get(0)?.scoreDetails?.wickets.toString()}"
                                tvTeam1Overs.text =
                                    "(${scorecard?.get(0)?.scoreDetails?.overs.toString()})"

                                tvTeam2Runs.text = scorecard?.get(1)?.scoreDetails?.runs.toString()
                                tvTeam2Wickets.text =
                                    "-${scorecard?.get(1)?.scoreDetails?.wickets.toString()}"
                                tvTeam2Overs.text =
                                    "(${scorecard?.get(1)?.scoreDetails?.overs.toString()})"

                                tvTotalExtras1.text = "${scorecard?.get(0)?.extrasData?.total}"
                                tvTotalExtras2.text = "${scorecard?.get(1)?.extrasData?.total}"

                                tvExtraRuns1.text = "b ${scorecard?.get(0)?.extrasData?.byes}" +
                                        ", lb ${scorecard?.get(0)?.extrasData?.legByes}" +
                                        ", w ${scorecard?.get(0)?.extrasData?.wides}" +
                                        ", nb ${scorecard?.get(0)?.extrasData?.noBalls}" +
                                        ", p ${scorecard?.get(0)?.extrasData?.penalty}"


                                tvExtraRuns.text = "${scorecard?.get(1)?.extrasData?.total}" +
                                        "b ${scorecard?.get(1)?.extrasData?.byes}" +
                                        ", lb ${scorecard?.get(1)?.extrasData?.legByes}" +
                                        ", w ${scorecard?.get(1)?.extrasData?.wides}" +
                                        ", nb ${scorecard?.get(1)?.extrasData?.noBalls}" +
                                        ", p ${scorecard?.get(1)?.extrasData?.penalty}"

                                val batList: ArrayList<Bat?> = ArrayList()
                                val bowlList: ArrayList<Bowl?> = ArrayList()
                                val wicketList: ArrayList<Wkt?> = ArrayList()
                                val partnershipList: ArrayList<Partnerships?> = ArrayList()

                                val batList1: ArrayList<Bat?> = ArrayList()
                                val bowlList1: ArrayList<Bowl?> = ArrayList()
                                val wicketList1: ArrayList<Wkt?> = ArrayList()
                                val partnershipList1: ArrayList<Partnerships?> = ArrayList()

                                if (scorecard?.isNotEmpty() == true && scorecard.size == 1) {
                                    scorecard[0].let { score ->
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_1)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_2)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_3)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_4)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_5)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_6)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_7)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_8)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_9)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_10)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_11)

                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_1)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_2)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_3)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_4)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_5)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_6)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_7)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_8)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_9)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_10)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_11)

                                        wicketList.add(score.wicketsData?.wkt_1)
                                        wicketList.add(score.wicketsData?.wkt_2)
                                        wicketList.add(score.wicketsData?.wkt_3)
                                        wicketList.add(score.wicketsData?.wkt_4)
                                        wicketList.add(score.wicketsData?.wkt_5)
                                        wicketList.add(score.wicketsData?.wkt_6)
                                        wicketList.add(score.wicketsData?.wkt_7)
                                        wicketList.add(score.wicketsData?.wkt_8)
                                        wicketList.add(score.wicketsData?.wkt_9)
                                        wicketList.add(score.wicketsData?.wkt_10)

                                        partnershipList.add(score.partnershipsData?.pat_1)
                                        partnershipList.add(score.partnershipsData?.pat_2)
                                        partnershipList.add(score.partnershipsData?.pat_3)
                                        partnershipList.add(score.partnershipsData?.pat_4)
                                        partnershipList.add(score.partnershipsData?.pat_5)
                                        partnershipList.add(score.partnershipsData?.pat_6)
                                        partnershipList.add(score.partnershipsData?.pat_7)
                                        partnershipList.add(score.partnershipsData?.pat_8)
                                        partnershipList.add(score.partnershipsData?.pat_9)
                                        partnershipList.add(score.partnershipsData?.pat_10)
                                    }
                                }

                                if (scorecard?.isNotEmpty() == true && scorecard.size == 2) {

                                    scorecard[0].let { score ->
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_1)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_2)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_3)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_4)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_5)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_6)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_7)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_8)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_9)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_10)
                                        batList.add(score.batTeamDetails?.batsmenData?.bat_11)

                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_1)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_2)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_3)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_4)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_5)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_6)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_7)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_8)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_9)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_10)
                                        bowlList.add(score.bowlTeamDetails?.bowlersData?.bowl_11)

                                        wicketList.add(score.wicketsData?.wkt_1)
                                        wicketList.add(score.wicketsData?.wkt_2)
                                        wicketList.add(score.wicketsData?.wkt_3)
                                        wicketList.add(score.wicketsData?.wkt_4)
                                        wicketList.add(score.wicketsData?.wkt_5)
                                        wicketList.add(score.wicketsData?.wkt_6)
                                        wicketList.add(score.wicketsData?.wkt_7)
                                        wicketList.add(score.wicketsData?.wkt_8)
                                        wicketList.add(score.wicketsData?.wkt_9)
                                        wicketList.add(score.wicketsData?.wkt_10)

                                        partnershipList.add(score.partnershipsData?.pat_1)
                                        partnershipList.add(score.partnershipsData?.pat_2)
                                        partnershipList.add(score.partnershipsData?.pat_3)
                                        partnershipList.add(score.partnershipsData?.pat_4)
                                        partnershipList.add(score.partnershipsData?.pat_5)
                                        partnershipList.add(score.partnershipsData?.pat_6)
                                        partnershipList.add(score.partnershipsData?.pat_7)
                                        partnershipList.add(score.partnershipsData?.pat_8)
                                        partnershipList.add(score.partnershipsData?.pat_9)
                                        partnershipList.add(score.partnershipsData?.pat_10)
                                    }

                                    scorecard[1].let { score ->
                                        batList1.add(score.batTeamDetails?.batsmenData?.bat_1)
                                        batList1.add(score.batTeamDetails?.batsmenData?.bat_2)
                                        batList1.add(score.batTeamDetails?.batsmenData?.bat_3)
                                        batList1.add(score.batTeamDetails?.batsmenData?.bat_4)
                                        batList1.add(score.batTeamDetails?.batsmenData?.bat_5)
                                        batList1.add(score.batTeamDetails?.batsmenData?.bat_6)
                                        batList1.add(score.batTeamDetails?.batsmenData?.bat_7)
                                        batList1.add(score.batTeamDetails?.batsmenData?.bat_8)
                                        batList1.add(score.batTeamDetails?.batsmenData?.bat_9)
                                        batList1.add(score.batTeamDetails?.batsmenData?.bat_10)
                                        batList1.add(score.batTeamDetails?.batsmenData?.bat_11)

                                        bowlList1.add(score.bowlTeamDetails?.bowlersData?.bowl_1)
                                        bowlList1.add(score.bowlTeamDetails?.bowlersData?.bowl_2)
                                        bowlList1.add(score.bowlTeamDetails?.bowlersData?.bowl_3)
                                        bowlList1.add(score.bowlTeamDetails?.bowlersData?.bowl_4)
                                        bowlList1.add(score.bowlTeamDetails?.bowlersData?.bowl_5)
                                        bowlList1.add(score.bowlTeamDetails?.bowlersData?.bowl_6)
                                        bowlList1.add(score.bowlTeamDetails?.bowlersData?.bowl_7)
                                        bowlList1.add(score.bowlTeamDetails?.bowlersData?.bowl_8)
                                        bowlList1.add(score.bowlTeamDetails?.bowlersData?.bowl_9)
                                        bowlList1.add(score.bowlTeamDetails?.bowlersData?.bowl_10)
                                        bowlList1.add(score.bowlTeamDetails?.bowlersData?.bowl_11)

                                        wicketList1.add(score.wicketsData?.wkt_1)
                                        wicketList1.add(score.wicketsData?.wkt_2)
                                        wicketList1.add(score.wicketsData?.wkt_3)
                                        wicketList1.add(score.wicketsData?.wkt_4)
                                        wicketList1.add(score.wicketsData?.wkt_5)
                                        wicketList1.add(score.wicketsData?.wkt_6)
                                        wicketList1.add(score.wicketsData?.wkt_7)
                                        wicketList1.add(score.wicketsData?.wkt_8)
                                        wicketList1.add(score.wicketsData?.wkt_9)
                                        wicketList1.add(score.wicketsData?.wkt_10)

                                        partnershipList1.add(score.partnershipsData?.pat_1)
                                        partnershipList1.add(score.partnershipsData?.pat_2)
                                        partnershipList1.add(score.partnershipsData?.pat_3)
                                        partnershipList1.add(score.partnershipsData?.pat_4)
                                        partnershipList1.add(score.partnershipsData?.pat_5)
                                        partnershipList1.add(score.partnershipsData?.pat_6)
                                        partnershipList1.add(score.partnershipsData?.pat_7)
                                        partnershipList1.add(score.partnershipsData?.pat_8)
                                        partnershipList1.add(score.partnershipsData?.pat_9)
                                        partnershipList1.add(score.partnershipsData?.pat_10)
                                    }

                                }

                                batterAdapter =
                                    BatterScorecardAdapter(
                                        this@ScorecardFragment,
                                        batList
                                    )

                                bowlerAdapter =
                                    BowlerScorecardAdapter(
                                        this@ScorecardFragment,
                                        bowlList
                                    )

                                fallOfWicketsAdapter =
                                    FallOfWicketsScorecardAdapter(
                                        this@ScorecardFragment,
                                        wicketList
                                    )

                                partnershipsAdapter =
                                    PartnershipScorecardAdapter(
                                        this@ScorecardFragment,
                                        partnershipList
                                    )

                                batterAdapter1 =
                                    BatterScorecardAdapter(
                                        this@ScorecardFragment,
                                        batList1
                                    )

                                bowlerAdapter1 =
                                    BowlerScorecardAdapter(
                                        this@ScorecardFragment,
                                        bowlList1
                                    )

                                fallOfWicketsAdapter1 =
                                    FallOfWicketsScorecardAdapter(
                                        this@ScorecardFragment,
                                        wicketList1
                                    )

                                partnershipsAdapter1 =
                                    PartnershipScorecardAdapter(
                                        this@ScorecardFragment,
                                        partnershipList1
                                    )
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
        private const val TAG = "ScorecardFragment"
    }
}
