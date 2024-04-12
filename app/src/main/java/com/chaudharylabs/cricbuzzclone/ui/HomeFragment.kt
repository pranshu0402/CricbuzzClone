package com.chaudharylabs.cricbuzzclone.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private var list: ArrayList<Matche> = ArrayList()
    private var newList: ArrayList<Matche> = ArrayList()
    private var teamImageUrlList: ArrayList<String> = ArrayList()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var matchesViewModel: MatchesViewModel
    private lateinit var topStoriesViewModel: TopStoriesViewModel
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

        matchesViewModel = ViewModelProvider(this)[MatchesViewModel::class.java]
        topStoriesViewModel = ViewModelProvider(this)[TopStoriesViewModel::class.java]

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
                        Log.d(TAG, "liveMatches response Success :: $it")
                        getList(it)
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "liveMatches response Error :: ${response.message}")
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
                        Log.d(TAG, "recentMatches response Success :: $it")
                        getList(it)
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "recentMatches response Error :: ${response.message}")
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
                        Log.d(TAG, "upcomingMatches response Success :: $it")
                        getList(it)
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "upcomingMatches response Error :: ${response.message}")
                }

            }
        }

    private fun getList(matchesResponse: MatchesResponse?) {
        lifecycleScope.launch(Dispatchers.IO) {
            matchesResponse?.let { matches ->

                matches.typeMatches?.forEach { a ->
                    a.seriesMatches?.forEach { b ->
                        b.seriesAdWrapper?.matches?.forEach {
                            list.add(it)
                        }
                    }
                }

                getFormattedList(list)
            }
        }
    }

    private fun getFormattedList(list: ArrayList<Matche>?) {

        lifecycleScope.launch(Dispatchers.IO) {
            // invoke some suspend functions or execute potentially long running code

            val currentDate = sdf.format(System.currentTimeMillis())
            val firstDate: Date? = sdf.parse(currentDate)

            list?.forEach {

                val secondDate: Date? = sdf.parse(sdf.format(it.matchInfo?.startDate?.toLong()))

                val cmp = firstDate?.compareTo(secondDate)
                if (cmp != null) {
                    when {
                        cmp > 0 -> {
                            newList.add(it)
                        }

                        cmp < 0 -> {}

                        else -> {
                            newList.add(it)
                        }
                    }
                }

            }

            matchesViewModel.list.postValue(newList.distinct()
                .filter { it.matchInfo?.isFantasyEnabled == true } as ArrayList<Matche>)

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

    override fun onDestroyView() {
        super.onDestroyView()

    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}