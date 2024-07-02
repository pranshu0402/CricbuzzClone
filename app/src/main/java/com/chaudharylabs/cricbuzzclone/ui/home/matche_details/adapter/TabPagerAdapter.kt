package com.chaudharylabs.cricbuzzclone.ui.home.matche_details.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.InfoFragment
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.LiveFragment
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.MatchDetailsFragment
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.OversFragment
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.ScorecardFragment
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.SquadsFragment


class TabPagerAdapter(var presenter: MatchDetailsFragment) : FragmentStateAdapter(presenter) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InfoFragment()
            1 -> LiveFragment()
            2 -> ScorecardFragment()
            3 -> SquadsFragment()
            4 -> OversFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }

    override fun getItemCount(): Int {
        return TABS
    }

    companion object {
        private const val TABS = 5
    }
}