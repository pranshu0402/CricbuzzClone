package com.chaudharylabs.cricbuzzclone.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.model.Matche
import com.chaudharylabs.cricbuzzclone.data.model.MatchesResponse
import com.chaudharylabs.cricbuzzclone.databinding.FragmentHomeBinding
import com.chaudharylabs.cricbuzzclone.ui.adapters.HomeBannerAdapter
import com.chaudharylabs.cricbuzzclone.ui.utils.DotsIndicatorDecoration
import com.chaudharylabs.cricbuzzclone.viewmodels.MatchesViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var currentItemIndex = 0
    private var autoScrollHandler: Handler? = null
    private var list: ArrayList<Matche> = ArrayList()
    private lateinit var binding: FragmentHomeBinding
    private val matchesViewModel: MatchesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            presenter = this@HomeFragment
            lifecycleOwner = this@HomeFragment
            executePendingBindings()
        }

        getMatches()

        matchesViewModel.list.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                loadBanners(it)
            }
        }
    }

    private fun getMatches() {
        lifecycleScope.launch {
            matchesViewModel.getLiveMatches().collect(liveMatchesCallback)
            matchesViewModel.getRecentMatches().collect(recentMatchesCallback)
            // matchesViewModel.getUpcomingMatches().collect(upcomingMatchesCallback)
        }
    }

    private val liveMatchesCallback: FlowCollector<NetworkResult<MatchesResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")
                        getList(it)
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "response Error :: ${response.message}")
                }

            }
        }

    private val recentMatchesCallback: FlowCollector<NetworkResult<MatchesResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")
                        getList(it)
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "response Error :: ${response.message}")
                }

            }
        }

    private val upcomingMatchesCallback: FlowCollector<NetworkResult<MatchesResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")
                        getList(it)
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "response Error :: ${response.message}")
                }

            }
        }

    private fun getList(matchesResponse: MatchesResponse?) {
        matchesResponse?.let { matches ->
            val league = matches.filters?.matchType?.find { a -> a == "League" }

            matches.typeMatches?.forEach { a ->
                if (a.matchType == league) {
                    a.seriesMatches?.forEach { b ->
                        if (b.seriesAdWrapper?.seriesName == "Indian Premier League 2024") {
                            b.seriesAdWrapper.matches?.forEach {
                                list.add(it)
                            }

                            matchesViewModel.list.value = list

                            Log.d(TAG, "list :: ${matchesViewModel.list.value?.size}")
                        }
                    }
                }
            }
        }
    }

    private fun loadBanners(matchesResponse: ArrayList<Matche>) {
        binding.apply {

            matchesResponse.let { matches ->
                bannerAdapter =
                    activity?.let {
                        HomeBannerAdapter(
                            this@HomeFragment,
                            matches,
                            it
                        )
                    }
            }

            rvBannerList.setHasFixedSize(true)
            rvBannerList.onFlingListener = null
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(rvBannerList)

            rvBannerList.let {
                if (it.itemDecorationCount < 1) {
                    it.addItemDecoration(
                        DotsIndicatorDecoration(
                            "#FFFFFF",
                            "#4DFFFFFF"
                        )
                    )
                }
            }
            rvBannerList.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        autoScrollHandler?.post(autoScrollRunnable)
    }

    private var autoScrollRunnable: Runnable = object : Runnable {
        override fun run() {
            val maxCount = binding.rvBannerList.adapter?.itemCount
            if (maxCount != null && currentItemIndex < maxCount) {
                binding.rvBannerList.smoothScrollToPosition(currentItemIndex++)
                autoScrollHandler?.postDelayed(this, 4000)
            } else {
                currentItemIndex = 0
                autoScrollHandler?.post(this)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (autoScrollHandler != null) {
            autoScrollHandler?.removeCallbacks(autoScrollRunnable)
        }

        binding.unbind()
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}