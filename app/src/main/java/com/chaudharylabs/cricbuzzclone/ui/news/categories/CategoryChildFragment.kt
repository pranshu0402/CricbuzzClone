package com.chaudharylabs.cricbuzzclone.ui.news.categories

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
import com.chaudharylabs.cricbuzzclone.data.model.news.categories.CategoryResponse
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.TopStoriesResponse
import com.chaudharylabs.cricbuzzclone.databinding.FragmentCategoryChildBinding
import com.chaudharylabs.cricbuzzclone.ui.BaseFragment
import com.chaudharylabs.cricbuzzclone.ui.news.NewsViewModel
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class CategoryChildFragment : BaseFragment() {

    private var storyType: CategoryResponse.StoryType? = null
    private lateinit var binding: FragmentCategoryChildBinding
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            storyType = it.getParcelable(Constants.STORY_ID)
            Log.d(TAG, "story:- $storyType")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_category_child, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setBottomNavVisibility(View.VISIBLE)
            presenter = this@CategoryChildFragment
            lifecycleOwner = this@CategoryChildFragment
            executePendingBindings()
            tvTitle.text = storyType?.name
        }

        lifecycleScope.launch {
            viewModel.getListByCategory(storyType?.id.toString()).collect(categoriesCallback)
        }
    }

    private val categoriesCallback: FlowCollector<NetworkResult<TopStoriesResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")

                        if (!it.storyList.isNullOrEmpty()) {
                            binding.adapter =
                                CategoryChildAdapter(
                                    this@CategoryChildFragment,
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

        if (findNavController().currentDestination?.label == getString(R.string.fragment_categories_child) && isAdded) {
            findNavController().safeNavigateWithArgs(
                CategoryChildFragmentDirections.actionCategoryChildFragmentToStoryDetailsFragment(),
                bundle
            )
        }
    }

    fun back() {
        requireActivity().onBackPressed()
    }

    companion object {
        private const val TAG = "CategoriesFragment"
    }
}