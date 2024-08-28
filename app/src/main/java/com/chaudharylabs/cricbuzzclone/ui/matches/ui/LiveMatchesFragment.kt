package com.chaudharylabs.cricbuzzclone.ui.matches.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.databinding.FragmentLiveMatchesBinding
import com.chaudharylabs.cricbuzzclone.ui.BaseFragment

class LiveMatchesFragment : BaseFragment() {

    private lateinit var binding: FragmentLiveMatchesBinding

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
        setBottomNavVisibility(View.VISIBLE)


    }

    companion object {
        private const val TAG = "LiveMatchesFragment"
    }
}