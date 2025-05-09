package com.chaudharylabs.cricbuzzclone.ui.matches.live

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.matches.SeriesMatche
import com.chaudharylabs.cricbuzzclone.databinding.LytLiveSeriesMatchesBinding

class LiveSeriesMatchesAdapter(
    private var liveMatchesFragment: LiveMatchesFragment,
    private var list: List<SeriesMatche>
) :
    RecyclerView.Adapter<LiveSeriesMatchesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytLiveSeriesMatchesBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_live_series_matches, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytLiveSeriesMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(seriesMatche: SeriesMatche?) {
            binding.apply {
                txtSeriesName.text = seriesMatche?.seriesAdWrapper?.seriesName

                if (seriesMatche != null) {
                    this.liveMatchesFragmentPresenter = liveMatchesFragment
                    rvMatches.adapter =
                        seriesMatche.seriesAdWrapper?.matches?.let { matches ->
                            LiveMatchesAdapter(liveMatchesFragment, matches)
                        }
                }
            }
        }
    }

}