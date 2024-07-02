package com.chaudharylabs.cricbuzzclone.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.databinding.FragmentNewsBinding
import com.chaudharylabs.cricbuzzclone.ui.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class NewsFragment : BaseFragment() {

    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setBottomNavVisibility(View.VISIBLE)
            presenter = this@NewsFragment
            lifecycleOwner = this@NewsFragment
            executePendingBindings()

            viewPager.adapter = NewsTabPagerAdapter(this@NewsFragment)

            val tabs = listOf(
                this@NewsFragment.getString(R.string.all_stories),
                this@NewsFragment.getString(R.string.premium_fragment),
                this@NewsFragment.getString(R.string.categories_fragment),
                this@NewsFragment.getString(R.string.topics_fragment),
            )

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabs[position]
            }.attach()
        }
    }

    fun back() {
        requireActivity().onBackPressed()
    }

    companion object {
        private const val TAG = "NewsFragment"
    }
}