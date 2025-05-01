package com.chaudharylabs.cricbuzzclone.ui.matches.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.model.matches.MatchesResponse
import com.chaudharylabs.cricbuzzclone.databinding.FragmentLiveMatchesBinding
import com.chaudharylabs.cricbuzzclone.databinding.FragmentRecentMatchesBinding
import com.chaudharylabs.cricbuzzclone.ui.BaseFragment
import com.chaudharylabs.cricbuzzclone.ui.matches.adapter.TypeMatcheAdapter
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class RecentMatchesFragment : BaseFragment() {
    private lateinit var binding: FragmentRecentMatchesBinding
    private lateinit var matchesTabViewModel: MatchesTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_recent_matches, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            presenter = this@RecentMatchesFragment
            lifecycleOwner = this@RecentMatchesFragment
            executePendingBindings()
        }
        setBottomNavVisibility(View.VISIBLE)

        matchesTabViewModel = ViewModelProvider(this)[MatchesTabViewModel::class.java]

        setupChip()

        getLiveMatches()
    }

    private fun setupChip() {
        val nameList =
            arrayListOf("All", "International", "League", "Domestic", "Women")
        for (name in nameList) {
            val chip = createChip(name)
            binding.cgChip.addView(chip)
        }
    }

    private fun createChip(label: String): Chip {
        val chip = requireActivity().layoutInflater.inflate(
            R.layout.lyt_choice_chip,
            null,
            false
        ) as Chip
        chip.text = label
        chip.isChipIconVisible = false
        return chip
    }

    private fun getLiveMatches() {
        lifecycleScope.launch(Dispatchers.IO) {
            matchesTabViewModel.getRecentMatches().collect(liveMatchesCallback)
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
                        if (it.typeMatches != null) {
                            lifecycleScope.launch {
                                binding.rvTypeMatches.adapter =
                                    TypeMatcheAdapter(
                                        null,
                                        null,
                                        this@RecentMatchesFragment,
                                        it.typeMatches
                                    )
                            }
                        }
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "liveMatches response Error :: ${response.message}")
                }

            }
        }

    companion object {
        private const val TAG = "RecentMatchesFragment"
    }
}