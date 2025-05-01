package com.chaudharylabs.cricbuzzclone.ui.matches.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.matches.SeriesMatche
import com.chaudharylabs.cricbuzzclone.databinding.LytSeriesMatchesBinding
import com.chaudharylabs.cricbuzzclone.ui.matches.ui.LiveMatchesFragment
import com.chaudharylabs.cricbuzzclone.ui.matches.ui.RecentMatchesFragment
import com.chaudharylabs.cricbuzzclone.ui.matches.ui.UpcomingMatchesFragment

class SeriesMatchesAdapter(
    private var liveMatchesFragment: LiveMatchesFragment?,
    private var upcomingMatchesFragment: UpcomingMatchesFragment?,
    private var recentMatchesFragment: RecentMatchesFragment?,
    private var list: List<SeriesMatche>
) :
    RecyclerView.Adapter<SeriesMatchesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytSeriesMatchesBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_series_matches, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytSeriesMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(seriesMatche: SeriesMatche?) {
            binding.apply {
                txtSeriesName.text = seriesMatche?.seriesAdWrapper?.seriesName

                if (seriesMatche != null) {
                    if (liveMatchesFragment != null) {
                        this.liveMatchesFragmentPresenter = liveMatchesFragment
                        rvMatches.adapter =
                            seriesMatche.seriesAdWrapper?.matches?.let { matches ->
                                liveMatchesFragment?.let {
                                    MatchesAdapter(
                                        it,
                                        null,
                                        null,
                                        matches
                                    )
                                }
                            }
                    }

                    if (upcomingMatchesFragment != null) {
                        this.upcomingMatchesFragmentPresenter = upcomingMatchesFragment
                        rvMatches.adapter =
                            seriesMatche.seriesAdWrapper?.matches?.let { matches ->
                                upcomingMatchesFragment?.let {
                                    MatchesAdapter(
                                        null,
                                        it,
                                        null,
                                        matches
                                    )
                                }
                            }
                    }

                    if (recentMatchesFragment != null) {
                        this.recentMatchesFragmentPresenter = recentMatchesFragment
                        rvMatches.adapter =
                            seriesMatche.seriesAdWrapper?.matches?.let { matches ->
                                recentMatchesFragment?.let {
                                    MatchesAdapter(
                                        null,
                                        null,
                                        it,
                                        matches
                                    )
                                }
                            }
                    }
                }
            }
        }
    }

}