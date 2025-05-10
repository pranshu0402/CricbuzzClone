package com.chaudharylabs.cricbuzzclone.ui.matches.recent

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
import com.chaudharylabs.cricbuzzclone.data.model.matches.TypeMatche
import com.chaudharylabs.cricbuzzclone.databinding.FragmentRecentMatchesBinding
import com.chaudharylabs.cricbuzzclone.ui.BaseFragment
import com.chaudharylabs.cricbuzzclone.ui.matches.MatchesFragmentDirections
import com.chaudharylabs.cricbuzzclone.ui.matches.MatchesTabViewModel
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants.MATCH
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class RecentMatchesFragment : BaseFragment() {
    private lateinit var binding: FragmentRecentMatchesBinding
    private lateinit var matchesTabViewModel: MatchesTabViewModel
    private var adapter: RecentTypeMatcheAdapter? = null
    private var typeMatchList: ArrayList<TypeMatche>? = ArrayList()
    private var filteredMatches: ArrayList<TypeMatche>? = ArrayList()

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
        adapter = RecentTypeMatcheAdapter(this@RecentMatchesFragment)
        binding.rvTypeMatches.adapter = adapter
        setupChip()

        getRecentMatches()
    }

    private fun setupChip() {
        binding.cgChip.setOnCheckedStateChangeListener { group, checkedIds ->
            val c: Chip = binding.cgChip.findViewById(binding.cgChip.checkedChipId)

            if (c.text == "All") {
                typeMatchList?.let { adapter?.filterList(it) }
            } else {
                filteredMatches =
                    typeMatchList?.filter { it.matchType == c.text } as ArrayList<TypeMatche>

                if (filteredMatches?.isEmpty() == true) {
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.rvTypeMatches.visibility = View.GONE
                } else {
                    binding.tvEmpty.visibility = View.GONE
                    binding.rvTypeMatches.visibility = View.VISIBLE
                    filteredMatches?.let {
                        adapter?.filterList(it)
                    }
                }
            }
        }
    }


    private fun getRecentMatches() {
        lifecycleScope.launch(Dispatchers.IO) {
            matchesTabViewModel.getRecentMatches().collect(liveMatchesCallback)
        }
    }

    fun goToLive(matche: Matche) {
        val bundle = Bundle()
        bundle.putParcelable(MATCH, matche)

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
                                typeMatchList = it.typeMatches as ArrayList<TypeMatche>
                                adapter?.filterList(it.typeMatches)
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