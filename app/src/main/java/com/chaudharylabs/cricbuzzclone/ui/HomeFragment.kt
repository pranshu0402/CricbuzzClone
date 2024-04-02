package com.chaudharylabs.cricbuzzclone.ui

import android.os.Bundle
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
import com.chaudharylabs.cricbuzzclone.data.model.matches.Matche
import com.chaudharylabs.cricbuzzclone.data.model.matches.MatchesResponse
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.TopStoriesResponse
import com.chaudharylabs.cricbuzzclone.databinding.FragmentHomeBinding
import com.chaudharylabs.cricbuzzclone.ui.adapters.HomeBannerAdapter
import com.chaudharylabs.cricbuzzclone.ui.adapters.TopStoriesAdapter
import com.chaudharylabs.cricbuzzclone.ui.utils.DotsIndicatorDecoration
import com.chaudharylabs.cricbuzzclone.viewmodels.MatchesViewModel
import com.chaudharylabs.cricbuzzclone.viewmodels.TopStoriesViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment() {
    private var list: ArrayList<Matche> = ArrayList()
    private var teamImageUrlList: ArrayList<String> = ArrayList()
    private lateinit var binding: FragmentHomeBinding
    private val matchesViewModel: MatchesViewModel by activityViewModels()
    private val topStoriesViewModel: TopStoriesViewModel by activityViewModels()
    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

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

        getTeamImageUrlList()

        getMatches()

        getTopStories()

        matchesViewModel.list.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                loadBanners(it)
            }
        }
    }

    private fun getTopStories() {
        lifecycleScope.launch {
            topStoriesViewModel.getTopStories().collect(topStoriesCallback)
        }
    }

    private val topStoriesCallback: FlowCollector<NetworkResult<TopStoriesResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")

                        if (!it.storyList.isNullOrEmpty()) {
                            binding.topStoriesAdapter =
                                TopStoriesAdapter(
                                    this@HomeFragment,
                                    it.storyList.filter { it.story != null })
                        }
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "response Error :: ${response.message}")
                }

            }
        }

    private fun getTeamImageUrlList() {
        teamImageUrlList.clear()
        teamImageUrlList.add("https://static.cricbuzz.com/a/img/v1/24x18/i1/c225641/chennai-super-kings.jpg")
        teamImageUrlList.add("https://static.cricbuzz.com/a/img/v1/24x18/i1/c225647/rajasthan-royals.jpg")
        teamImageUrlList.add("https://static.cricbuzz.com/a/img/v1/24x18/i1/c225646/kolkata-knight-riders.jpg")
        teamImageUrlList.add("https://static.cricbuzz.com/a/img/v1/24x18/i1/c225648/punjab-kings.jpg")
        teamImageUrlList.add("https://static.cricbuzz.com/a/img/v1/24x18/i1/c225643/royal-challengers-bengaluru.jpg")
        teamImageUrlList.add("https://static.cricbuzz.com/a/img/v1/24x18/i1/c235085/gujarat-titans.jpg")
        teamImageUrlList.add("https://static.cricbuzz.com/a/img/v1/24x18/i1/c225644/delhi-capitals.jpg")
        teamImageUrlList.add("https://static.cricbuzz.com/a/img/v1/24x18/i1/c389444/lucknow-super-giants.jpg")
        teamImageUrlList.add("https://static.cricbuzz.com/a/img/v1/24x18/i1/c225645/mumbai-indians.jpg")
        teamImageUrlList.add("https://static.cricbuzz.com/a/img/v1/24x18/i1/c225649/sunrisers-hyderabad.jpg")
    }

    private fun getMatches() {
        lifecycleScope.launch {
            matchesViewModel.getLiveMatches().collect(liveMatchesCallback)
            matchesViewModel.getUpcomingMatches().collect(upcomingMatchesCallback)
            matchesViewModel.getRecentMatches().collect(recentMatchesCallback)
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
                        it.let { matches ->
                            val league = matches.filters?.matchType?.find { a -> a == "League" }

                            matches.typeMatches?.forEach { a ->
                                if (a.matchType == league) {
                                    a.seriesMatches?.forEach { b ->
                                        if (b.seriesAdWrapper?.seriesName == "Indian Premier League 2024") {
                                            b.seriesAdWrapper.matches?.forEach { matche ->
                                                val l = matche.matchInfo?.startDate?.toLongOrNull()
                                                if (l != null) {
                                                    if (sdf.format(l) == sdf.format(
                                                            System.currentTimeMillis()
                                                        )
                                                    ) {
                                                        list.add(matche)

                                                        matchesViewModel.list.value = list

                                                        Log.d(
                                                            TAG,
                                                            "list :: ${matchesViewModel.list.value?.size}, $list"
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
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
                            teamImageUrlList,
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

    companion object {
        private const val TAG = "HomeFragment"
    }
}