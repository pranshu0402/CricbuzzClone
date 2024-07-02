package com.chaudharylabs.cricbuzzclone.ui.home.matche_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.api.NetworkResult
import com.chaudharylabs.cricbuzzclone.data.model.match_details.info.MatchDetailsResponse
import com.chaudharylabs.cricbuzzclone.data.model.matches.Matche
import com.chaudharylabs.cricbuzzclone.databinding.FragmentInfoBinding
import com.chaudharylabs.cricbuzzclone.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class InfoFragment : BaseFragment() {
    private val viewModel: MatchesViewModel by activityViewModels()
    private lateinit var binding: FragmentInfoBinding
    private var matche: Matche? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            presenter = this@InfoFragment
            lifecycleOwner = this@InfoFragment
            executePendingBindings()
        }

        setBottomNavVisibility(View.GONE)

        matche = viewModel.match.value
        println(matche?.matchInfo?.matchId.toString())

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getMatchInfo(matche?.matchInfo?.matchId.toString()).collect(infoCallback)
        }
    }

    private val infoCallback: FlowCollector<NetworkResult<MatchDetailsResponse>> =
        FlowCollector { response ->
            when (response) {
                is NetworkResult.Loading -> {
                    binding.apply {
                        tvInfo.visibility = View.INVISIBLE
                        tvVenueGuide.visibility = View.INVISIBLE
                        tvGuide.visibility = View.INVISIBLE
                    }
                }

                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(TAG, "response Success :: $it")
                        lifecycleScope.launch {
                            binding.apply {

                                match = it

                                tvInfo.visibility = View.VISIBLE
                                tvVenueGuide.visibility = View.VISIBLE
                                tvGuide.visibility = View.VISIBLE

                                if (!it.broadcastInfo.isNullOrEmpty()) {
                                    it.broadcastInfo.forEach { a ->

                                        if (!a.broadcaster.isNullOrEmpty()) {
                                            a.broadcaster.forEach { b ->
                                                if (b?.broadcastType == "Streaming") {
                                                    tvStreamingName.text = b.value
                                                    tvStreaming.visibility = View.VISIBLE
                                                    tvStreamingName.visibility = View.VISIBLE
                                                    tvVenueGuide.visibility = View.VISIBLE
                                                }
                                                if (b?.broadcastType == "TV") {
                                                    tvTvName.text = b.value
                                                    tvTv.visibility = View.VISIBLE
                                                    tvTvName.visibility = View.VISIBLE
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    tvStreaming.visibility = View.GONE
                                    tvStreamingName.visibility = View.GONE
                                    tvTv.visibility = View.GONE
                                    tvTvName.visibility = View.GONE
                                    tvVenueGuide.visibility = View.GONE
                                }

                                tvDateValue.text =
                                    getFormattedDate(it.matchInfo?.matchStartTimestamp)
                                tvTimeValue.text =
                                    getFormattedTime(it.matchInfo?.matchStartTimestamp)

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
        private const val TAG = "InfoFragment"
    }
}