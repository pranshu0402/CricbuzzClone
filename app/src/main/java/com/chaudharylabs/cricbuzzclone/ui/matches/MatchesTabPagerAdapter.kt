package com.chaudharylabs.cricbuzzclone.ui.matches

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chaudharylabs.cricbuzzclone.ui.matches.live.LiveMatchesFragment
import com.chaudharylabs.cricbuzzclone.ui.matches.recent.RecentMatchesFragment
import com.chaudharylabs.cricbuzzclone.ui.matches.upcoming.UpcomingMatchesFragment


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