package com.chaudharylabs.cricbuzzclone.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
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
    ): View {
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

                            println(it.content?.size)

                            val l = it.content?.filter { a ->
                                a.ad == null && a.content != null && a.content.contentType == "text"
                            }

                            l?.let { list ->

                                lytData.removeAllViews()
                                lytData.orientation = LinearLayoutCompat.VERTICAL

                                for (element in list) {
                                    val tv = TextView(lytData.context)

                                    val params: LinearLayout.LayoutParams =
                                        LinearLayout.LayoutParams(
                                            LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                                            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                                        )
                                    params.setMargins(10, 10, 10, 10)
                                    tv.layoutParams = params

                                    tv.textSize = 15F
                                    tv.letterSpacing = 0.02f
                                    tv.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.white)))

                                    tv.text = "${element.content?.contentValue}"
                                    tv.typeface = ResourcesCompat.getFont(
                                        lytData.context,
                                        R.font.averta_regular
                                    )

                                    if (!it.format.isNullOrEmpty()) {
                                        it.format.forEach { format ->
                                            format.value?.forEach { value ->
                                                if (value?.id == "${element.content?.contentValue}") {
                                                    tv.text = "${value.value}"
                                                    tv.typeface = ResourcesCompat.getFont(
                                                        lytData.context,
                                                        R.font.averta_bold
                                                    )
                                                }
                                            }
                                        }
                                    }


                                    lytData.addView(tv)
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

    fun back(){
        requireActivity().onBackPressed()
    }

    companion object {
        private const val TAG = "StoryDetailsFragment"
    }
}