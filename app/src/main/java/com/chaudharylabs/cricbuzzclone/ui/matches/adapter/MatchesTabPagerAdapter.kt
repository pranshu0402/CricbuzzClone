package com.chaudharylabs.cricbuzzclone.ui.matches.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.InfoFragment
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.LiveFragment
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.MatchDetailsFragment
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.OversFragment
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.ScorecardFragment
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.SquadsFragment
import com.chaudharylabs.cricbuzzclone.ui.matches.MatchesFragment
import com.chaudharylabs.cricbuzzclone.ui.matches.ui.LiveMatchesFragment
import com.chaudharylabs.cricbuzzclone.ui.matches.ui.RecentMatchesFragment
import com.chaudharylabs.cricbuzzclone.ui.matches.ui.UpcomingMatchesFragment


class MatchesTabPagerAdapter(var presenter: MatchesFragment) : FragmentStateAdapter(presenter) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LiveMatchesFragment()
            1 -> UpcomingMatchesFragment()
            2 -> RecentMatchesFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }

    override fun getItemCount(): Int {
        return TABS
    }

    companion object {
        private const val TABS = 3
    }
}