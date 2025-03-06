package com.chaudharylabs.cricbuzzclone.ui.matches.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.matches.SeriesMatche
import com.chaudharylabs.cricbuzzclone.databinding.LytMatchesBinding
import com.chaudharylabs.cricbuzzclone.ui.matches.ui.LiveMatchesFragment


class LiveSeriesMatchesAdapter(
    private val liveMatchesFragment: LiveMatchesFragment,
    private val list: List<SeriesMatche>
) :
    RecyclerView.Adapter<LiveSeriesMatchesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytMatchesBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_matches, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(var binding: LytMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(seriesMatche: SeriesMatche?) {
            binding.apply {
                presenter = liveMatchesFragment
                txtSeriesName.text = seriesMatche?.seriesAdWrapper?.seriesName.toString()
                if (seriesMatche?.seriesAdWrapper?.matches != null) {
                    rvMatches.adapter =
                        LiveMatchesAdapter(
                            liveMatchesFragment,
                            seriesMatche.seriesAdWrapper.matches
                        )
                }
            }
        }
    }
}