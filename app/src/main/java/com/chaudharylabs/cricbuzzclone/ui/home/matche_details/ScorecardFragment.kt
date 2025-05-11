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

                            tvStatus.text = it.status

                            it.scoreCard.let { scorecard ->

                                if (scorecard?.isNotEmpty() == true) {

                                    if (scorecard.size == 1) {
                                        lytFirst.visibility = View.VISIBLE
                                        lytSecond.visibility = View.GONE
                                    }

                                    scorecard[0].let { score ->

                                        val batList: ArrayList<Bat?> = ArrayList()
                                        val bowlList: ArrayList<Bowl?> = ArrayList()
                                        val wicketList: ArrayList<Wkt?> = ArrayList()
                                        val partnershipList: ArrayList<Partnerships?> = ArrayList()


                                        tvTeam1Name.text =
                                            score.batTeamDetails?.batTeamName.toString()
                                        tvTeam1Runs.text = score.scoreDetails?.runs.toString()
                                        tvTeam1Wickets.text =
                                            "-${score.scoreDetails?.wickets.toString()}"
                                        tvTeam1Overs.text =
                                            "(${score.scoreDetails?.overs.toString()})"
                                        tvTotalExtras1.text = "${score.extrasData?.total}"

                                        tvExtraRuns1.text = "b ${score.extrasData?.byes}" +
                                                ", lb ${score.extrasData?.legByes}" +
                                                ", w ${score.extrasData?.wides}" +
                                                ", nb ${score.extrasData?.noBalls}" +
                                                ", p ${score.extrasData?.penalty}"

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

                                        batterAdapter =
                                            BatterScorecardAdapter(
                                                this@ScorecardFragment,
                                                batList.filter { batter -> batter?.outDesc != "" }
                                            )

                                        bowlerAdapter =
                                            BowlerScorecardAdapter(
                                                this@ScorecardFragment,
                                                bowlList.filterNotNull()
                                            )

                                        fallOfWicketsAdapter =
                                            FallOfWicketsScorecardAdapter(
                                                this@ScorecardFragment,
                                                wicketList.filterNotNull()
                                            )

                                        partnershipsAdapter =
                                            PartnershipScorecardAdapter(
                                                this@ScorecardFragment,
                                                partnershipList.filterNotNull()
                                            )
                                    }

                                    if (scorecard.size == 2) {

                                        lytFirst.visibility = View.VISIBLE
                                        lytSecond.visibility = View.VISIBLE

                                        scorecard[1].let { score ->

                                            val batList1: ArrayList<Bat?> = ArrayList()
                                            val bowlList1: ArrayList<Bowl?> = ArrayList()
                                            val wicketList1: ArrayList<Wkt?> = ArrayList()
                                            val partnershipList1: ArrayList<Partnerships?> =
                                                ArrayList()


                                            tvTeam2Name.text =
                                                score.batTeamDetails?.batTeamName.toString()
                                            tvTeam2Runs.text = score.scoreDetails?.runs.toString()
                                            tvTeam2Wickets.text =
                                                "-${score.scoreDetails?.wickets.toString()}"
                                            tvTeam2Overs.text =
                                                "(${score.scoreDetails?.overs.toString()})"
                                            tvTotalExtras2.text = "${score.extrasData?.total}"

                                            tvExtraRuns.text = "${score.extrasData?.total}" +
                                                    "b ${score.extrasData?.byes}" +
                                                    ", lb ${score.extrasData?.legByes}" +
                                                    ", w ${score.extrasData?.wides}" +
                                                    ", nb ${score.extrasData?.noBalls}" +
                                                    ", p ${score.extrasData?.penalty}"

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


                                            batterAdapter1 =
                                                BatterScorecardAdapter(
                                                    this@ScorecardFragment,
                                                    batList1.filter { batter -> batter?.outDesc != "" }
                                                )

                                            bowlerAdapter1 =
                                                BowlerScorecardAdapter(
                                                    this@ScorecardFragment,
                                                    bowlList1.filterNotNull()
                                                )

                                            fallOfWicketsAdapter1 =
                                                FallOfWicketsScorecardAdapter(
                                                    this@ScorecardFragment,
                                                    wicketList1.filterNotNull()
                                                )

                                            partnershipsAdapter1 =
                                                PartnershipScorecardAdapter(
                                                    this@ScorecardFragment,
                                                    partnershipList1.filterNotNull()
                                                )
                                        }
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

    companion object {
        private const val TAG = "ScorecardFragment"
    }
}
