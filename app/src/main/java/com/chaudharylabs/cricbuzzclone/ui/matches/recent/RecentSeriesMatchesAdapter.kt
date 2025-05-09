package com.chaudharylabs.cricbuzzclone.ui.matches.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.matches.SeriesMatche
import com.chaudharylabs.cricbuzzclone.databinding.LytRecentSeriesMatchesBinding

class RecentSeriesMatchesAdapter(
    private var recentMatchesFragment: RecentMatchesFragment,
    private var list: List<SeriesMatche>
) :
    RecyclerView.Adapter<RecentSeriesMatchesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytRecentSeriesMatchesBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_recent_series_matches, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytRecentSeriesMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(seriesMatch: SeriesMatche?) {
            binding.apply {
                txtSeriesName.text = seriesMatch?.seriesAdWrapper?.seriesName

                if (seriesMatch != null) {
                    this.presenter = recentMatchesFragment
                    rvMatches.adapter =
                        seriesMatch.seriesAdWrapper?.matches?.let { matches ->
                            RecentMatchesAdapter(recentMatchesFragment, matches)
                        }
                }
            }
        }
    }

}