package com.chaudharylabs.cricbuzzclone.ui.matches.live

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.model.matches.Matche
import com.chaudharylabs.cricbuzzclone.data.model.matches.MatchesResponse
import com.chaudharylabs.cricbuzzclone.databinding.FragmentLiveMatchesBinding
import com.chaudharylabs.cricbuzzclone.ui.BaseFragment
import com.chaudharylabs.cricbuzzclone.ui.matches.MatchesFragmentDirections
import com.chaudharylabs.cricbuzzclone.ui.matches.MatchesTabViewModel
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants.MATCH
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class LiveMatchesFragment : BaseFragment() {

    private lateinit var binding: FragmentLiveMatchesBinding
    private lateinit var matchesTabViewModel: MatchesTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_live_matches, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setBottomNavVisibility(View.VISIBLE)
            presenter = this@LiveMatchesFragment
            lifecycleOwner = this@LiveMatchesFragment
            executePendingBindings()
        }

        matchesTabViewModel = ViewModelProvider(this)[MatchesTabViewModel::class.java]

        setupChip()

        getLiveMatches()
    }

    private fun setupChip() {
        binding.cgChip.setOnCheckedStateChangeListener { group, checkedIds ->
            val c: Chip = binding.cgChip.findViewById(binding.cgChip.checkedChipId)
            println(c.text)
        }
    }

    private fun getLiveMatches() {
        lifecycleScope.launch(Dispatchers.IO) {
            matchesTabViewModel.getLiveMatches().collect(liveMatchesCallback)
        }
    }

    fun goToLive(match: Matche) {
        val bundle = Bundle()
        bundle.putParcelable(MATCH, match)

        lifecycleScope.launchWhenResumed {
            if (findNavController().currentDestination?.label == getString(R.string.fragment_matches) && isAdded) {
                findNavController().safeNavigateWithArgs(
                    MatchesFragmentDirections.actionMatchesFragmentToMatchDetailsFragment(),
                    bundle
                )
            }
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
                                    LiveTypeMatcheAdapter(this@LiveMatchesFragment, it.typeMatches)
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
        private const val TAG = "LiveMatchesFragment"
    }
}