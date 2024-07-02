package com.chaudharylabs.cricbuzzclone.ui.news

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chaudharylabs.cricbuzzclone.ui.news.all_stories.AllStoriesFragment
import com.chaudharylabs.cricbuzzclone.ui.news.categories.CategoriesFragment
import com.chaudharylabs.cricbuzzclone.ui.news.premium_editorials.PremiumNewsFragment
import com.chaudharylabs.cricbuzzclone.ui.news.topics.TopicsFragment


class NewsTabPagerAdapter(var presenter: NewsFragment) : FragmentStateAdapter(presenter) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllStoriesFragment()
            1 -> PremiumNewsFragment()
            2 -> CategoriesFragment()
            3 -> TopicsFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }

    override fun getItemCount(): Int {
        return TABS
    }

    companion object {
        private const val TABS = 4
    }
}