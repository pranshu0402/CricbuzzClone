package com.chaudharylabs.cricbuzzclone.ui.news.topics

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
import com.chaudharylabs.cricbuzzclone.data.model.news.topics.TopicsResponse
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.StoryX
import com.chaudharylabs.cricbuzzclone.databinding.FragmentTopicsBinding
import com.chaudharylabs.cricbuzzclone.ui.BaseFragment
import com.chaudharylabs.cricbuzzclone.ui.news.NewsFragmentDirections
import com.chaudharylabs.cricbuzzclone.ui.news.NewsViewModel
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class TopicsFragment : BaseFragment() {
    private lateinit var binding: FragmentTopicsBinding
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topics, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setBottomNavVisibility(View.VISIBLE)
            presenter = this@TopicsFragment
            lifecycleOwner = this@TopicsFragment
            executePendingBindings()
        }

        lifecycleScope.launch {
            viewModel.getTopics().collect(topicsCallback)
        }
    }

    private val topicsCallback: FlowCollector<NetworkResult<TopicsResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")

                        if (it.topics.isNotEmpty()) {
                            binding.adapter =
                                TopicsAdapter(
                                    this@TopicsFragment,
                                    it.topics
                                )
                        }
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "response Error :: ${response.message}")
                }

            }
        }

    fun topics(topic: TopicsResponse.Topic?) {
        Log.d(TAG, "storyId:- $topic")

        val bundle = Bundle()
        bundle.putParcelable(Constants.STORY_ID, topic)

        if (findNavController().currentDestination?.label == getString(R.string.fragment_news) && isAdded) {
            findNavController().safeNavigateWithArgs(
                NewsFragmentDirections.actionNewsFragmentToTopicsChildFragment(),
                bundle
            )
        }
    }

    companion object {
        private const val TAG = "TopicsFragment"
    }
}