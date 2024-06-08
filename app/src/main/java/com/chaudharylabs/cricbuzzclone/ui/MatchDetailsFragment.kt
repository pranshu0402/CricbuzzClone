package com.chaudharylabs.cricbuzzclone.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.matches.Matche
import com.chaudharylabs.cricbuzzclone.databinding.FragmentMatchDetailsBinding
import com.chaudharylabs.cricbuzzclone.ui.adapters.TabPagerAdapter
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants
import com.chaudharylabs.cricbuzzclone.viewmodels.MatchesViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MatchDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMatchDetailsBinding
    private var matche: Matche? = null
    private val viewModel: MatchesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            matche = it?.getParcelable(Constants.MATCH)
            Log.d(TAG, "match:-$matche")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_match_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            presenter = this@MatchDetailsFragment
            lifecycleOwner = this@MatchDetailsFragment
            executePendingBindings()

            tvTeamName.text =
                "${matche?.matchInfo?.team1?.teamSName} v ${matche?.matchInfo?.team2?.teamSName}"

            viewPager.adapter = TabPagerAdapter(this@MatchDetailsFragment)

            val tabs = listOf(
                this@MatchDetailsFragment.getString(R.string.info_fragment),
                this@MatchDetailsFragment.getString(R.string.live_fragment),
                this@MatchDetailsFragment.getString(R.string.scorecard_fragment),
                this@MatchDetailsFragment.getString(R.string.squads_fragment),
                this@MatchDetailsFragment.getString(R.string.overs_fragment)
            )

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabs[position]
            }.attach()
        }

        viewModel.match.value = matche
        println(viewModel.match.value?.matchInfo?.matchId)
    }

    fun back() {
        requireActivity().onBackPressed()
    }

    companion object {
        private const val TAG = "MatchDetailsFragment"
    }
}