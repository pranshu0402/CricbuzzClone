package com.chaudharylabs.cricbuzzclone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.databinding.FragmentMatchDetailsBinding
import com.chaudharylabs.cricbuzzclone.ui.adapters.TabPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MatchDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMatchDetailsBinding
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

            viewPager.adapter = TabPagerAdapter(this@MatchDetailsFragment)

            val tabs = listOf(
                this@MatchDetailsFragment.getString(R.string.info_fragment),
                this@MatchDetailsFragment.getString(R.string.live_fragment),
                this@MatchDetailsFragment.getString(R.string.scorecard_fragment),
                this@MatchDetailsFragment.getString(R.string.squads_fragment),
                this@MatchDetailsFragment.getString(R.string.overs_fragment),
                this@MatchDetailsFragment.getString(R.string.highlights_fragment),
            )

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabs[position]
            }.attach()
        }
    }

    companion object {
        private const val TAG = "MatchDetailsFragment"
    }
}