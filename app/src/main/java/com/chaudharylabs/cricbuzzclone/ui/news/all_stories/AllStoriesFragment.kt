package com.chaudharylabs.cricbuzzclone.ui.news.all_stories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.TopStoriesResponse
import com.chaudharylabs.cricbuzzclone.databinding.FragmentAllStoriesBinding
import com.chaudharylabs.cricbuzzclone.ui.BaseFragment
import com.chaudharylabs.cricbuzzclone.ui.news.NewsFragmentDirections
import com.chaudharylabs.cricbuzzclone.ui.news.NewsViewModel
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class AllStoriesFragment : BaseFragment() {

    private lateinit var binding: FragmentAllStoriesBinding
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_stories, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setBottomNavVisibility(View.VISIBLE)
            presenter = this@AllStoriesFragment
            lifecycleOwner = this@AllStoriesFragment
            executePendingBindings()
        }

        lifecycleScope.launch {
            viewModel.getAllStories().collect(allStoriesCallback)
        }
    }

    private val allStoriesCallback: FlowCollector<NetworkResult<TopStoriesResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")

                        if (!it.storyList.isNullOrEmpty()) {
                            binding.adapter =
                                AllStoriesAdapter(
                                    this@AllStoriesFragment,
                                    it.storyList.filter { it.story != null })
                        }
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "response Error :: ${response.message}")
                }

            }
        }

    fun storyDetails(storyId: String?) {
        Log.d(TAG, "storyId:- $storyId")

        val bundle = Bundle()
        bundle.putString(Constants.STORY_ID, storyId)

        if (findNavController().currentDestination?.label == getString(R.string.fragment_news) && isAdded) {
            findNavController().safeNavigateWithArgs(
                NewsFragmentDirections.actionNewsFragmentToStoryDetailsFragment(),
                bundle
            )
        } else {
            println(findNavController().currentDestination?.label)
            println(findNavController().currentDestination?.label)
        }
    }

    companion object {
        private const val TAG = "AllStoriesFragment"
    }
}