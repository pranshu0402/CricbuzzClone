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
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.StoryX
import com.chaudharylabs.cricbuzzclone.databinding.FragmentCategoriesBinding
import com.chaudharylabs.cricbuzzclone.ui.BaseFragment
import com.chaudharylabs.cricbuzzclone.ui.news.NewsFragmentDirections
import com.chaudharylabs.cricbuzzclone.ui.news.NewsViewModel
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class CategoriesFragment : BaseFragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setBottomNavVisibility(View.VISIBLE)
            presenter = this@CategoriesFragment
            lifecycleOwner = this@CategoriesFragment
            executePendingBindings()
        }

        lifecycleScope.launch {
            viewModel.getCategories().collect(categoriesCallback)
        }
    }

    private val categoriesCallback: FlowCollector<NetworkResult<CategoryResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")

                        if (it.storyType.isNotEmpty()) {
                            binding.adapter =
                                CategoryAdapter(
                                    this@CategoriesFragment,
                                    it.storyType
                                )
                        }
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "response Error :: ${response.message}")
                }

            }
        }

    fun category(categoryResponse: CategoryResponse.StoryType?) {
        Log.d(TAG, "story:- $categoryResponse")

        val bundle = Bundle()
        bundle.putParcelable(Constants.STORY_ID, categoryResponse)

        if (findNavController().currentDestination?.label == getString(R.string.fragment_news) && isAdded) {
            findNavController().safeNavigateWithArgs(
                NewsFragmentDirections.actionNewsFragmentToCategoryChildFragment(),
                bundle
            )
        }
    }

    companion object {
        private const val TAG = "CategoriesFragment"
    }
}