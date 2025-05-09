package com.chaudharylabs.cricbuzzclone.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.databinding.FragmentMatchesBinding
import com.chaudharylabs.cricbuzzclone.ui.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class MatchesFragment : BaseFragment() {

    private lateinit var binding: FragmentMatchesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_matches, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            presenter = this@MatchesFragment
            lifecycleOwner = this@MatchesFragment
            executePendingBindings()

            setBottomNavVisibility(View.GONE)
            viewPager.adapter = MatchesTabPagerAdapter(this@MatchesFragment)

            val tabs = listOf(
                this@MatchesFragment.getString(R.string.live_matches_fragment),
                this@MatchesFragment.getString(R.string.upcoming_matches_fragment),
                this@MatchesFragment.getString(R.string.recent_matches_fragment)
            )

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabs[position]
            }.attach()
        }
    }


    companion object {
        private const val TAG = "MatchesFragment"
    }
}