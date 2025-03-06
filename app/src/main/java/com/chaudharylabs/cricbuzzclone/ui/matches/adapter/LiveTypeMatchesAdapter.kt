package com.chaudharylabs.cricbuzzclone.ui.matches.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.matches.TypeMatche
import com.chaudharylabs.cricbuzzclone.databinding.LytSeriesMatchesBinding
import com.chaudharylabs.cricbuzzclone.ui.matches.ui.LiveMatchesFragment


class LiveTypeMatchesAdapter(
    private val liveMatchesFragment: LiveMatchesFragment,
    private val list: List<TypeMatche>
) :
    RecyclerView.Adapter<LiveTypeMatchesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytSeriesMatchesBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_series_matches, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(var binding: LytSeriesMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(typeMatche: TypeMatche?) {
            binding.apply {
                presenter = liveMatchesFragment
                if (typeMatche?.seriesMatches != null) {
                    rvSeriesMatches.adapter =
                        LiveSeriesMatchesAdapter(liveMatchesFragment, typeMatche.seriesMatches)
                }
            }
        }
    }
}