package com.chaudharylabs.cricbuzzclone.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.story_details.StoryDetailsResponse
import com.chaudharylabs.cricbuzzclone.databinding.FragmentStoryDetailsBinding
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants
import com.chaudharylabs.cricbuzzclone.viewmodels.TopStoriesViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch


class StoryDetailsFragment : BaseFragment() {

    private var storyId: String = ""
    private val url = "https://www.cricbuzz.com/a/img/v1/595x396/i1/c"
    private lateinit var binding: FragmentStoryDetailsBinding
    private lateinit var topStoriesViewModel: TopStoriesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            storyId = it.getString(Constants.STORY_ID).toString()
            Log.d(TAG, "storyId:- $storyId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_story_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            presenter = this@StoryDetailsFragment
            lifecycleOwner = this@StoryDetailsFragment
            executePendingBindings()
        }

        topStoriesViewModel = ViewModelProvider(this)[TopStoriesViewModel::class.java]

        getStoryDetails()
    }

    private fun getStoryDetails() {
        lifecycleScope.launch {
            topStoriesViewModel.getStoryDetails(storyId).collect(storyDetailsCallback)
        }
    }

    private val storyDetailsCallback: FlowCollector<NetworkResult<StoryDetailsResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")

                        binding.apply {
                            Glide.with(ivStoryPic.context)
                                .load("$url${it.coverImage?.id}/.jpg")
                                .into(ivStoryPic)

                            tvStoryTitle.text = it.headline
                            tvEditor.text = it.authors?.get(0)?.name
                            tvTimestamp.text = getDateFromMilliseconds(it.lastUpdatedTime?.toLong())

                            it.content?.let {
                                tvData1.text = it[0].content?.contentValue
                                tvData2.text = it[1].content?.contentValue
                                tvData3.text = it[2].content?.contentValue
                                tvData4.text = it[3].content?.contentValue
                                tvData5.text = it[4].content?.contentValue
                                tvData6.text = it[5].content?.contentValue
                            }
                        }
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "response Error :: ${response.message}")
                }

            }
        }

    companion object {
        private const val TAG = "StoryDetailsFragment"
    }
}