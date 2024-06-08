package com.chaudharylabs.cricbuzzclone.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chaudharylabs.cricbuzzclone.ui.InfoFragment
import com.chaudharylabs.cricbuzzclone.ui.LiveFragment
import com.chaudharylabs.cricbuzzclone.ui.MatchDetailsFragment
import com.chaudharylabs.cricbuzzclone.ui.OversFragment
import com.chaudharylabs.cricbuzzclone.ui.ScorecardFragment
import com.chaudharylabs.cricbuzzclone.ui.SquadsFragment


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