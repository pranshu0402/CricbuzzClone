package com.chaudharylabs.cricbuzzclone.ui.matches.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.matches.TypeMatche
import com.chaudharylabs.cricbuzzclone.databinding.LytTypeMatchesBinding
import com.chaudharylabs.cricbuzzclone.ui.matches.ui.LiveMatchesFragment
import com.chaudharylabs.cricbuzzclone.ui.matches.ui.RecentMatchesFragment
import com.chaudharylabs.cricbuzzclone.ui.matches.ui.UpcomingMatchesFragment

class TypeMatcheAdapter(
    private var liveMatchesFragment: LiveMatchesFragment?,
    private var upcomingMatchesFragment: UpcomingMatchesFragment?,
    private var recentMatchesFragment: RecentMatchesFragment?,
    private var list: List<TypeMatche>
) :
    RecyclerView.Adapter<TypeMatcheAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytTypeMatchesBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_type_matches, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytTypeMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(typeMatche: TypeMatche?) {
            binding.apply {
                txtMatchType.text = typeMatche?.matchType

                if (typeMatche != null) {
                    if (liveMatchesFragment != null) {
                        this.liveMatchesFragmentPresenter = liveMatchesFragment
                        rvSeriesMatches.adapter =
                            typeMatche.seriesMatches?.let { series ->
                                liveMatchesFragment?.let {
                                    SeriesMatchesAdapter(
                                        it,
                                        null,
                                        null,
                                        series
                                    )
                                }
                            }
                    }

                    if (upcomingMatchesFragment != null) {
                        this.upcomingMatchesFragmentPresenter = upcomingMatchesFragment
                        rvSeriesMatches.adapter =
                            typeMatche.seriesMatches?.let { series ->
                                upcomingMatchesFragment?.let {
                                    SeriesMatchesAdapter(
                                        null,
                                        it,
                                        null,
                                        series
                                    )
                                }
                            }
                    }

                    if (recentMatchesFragment != null) {
                        this.recentMatchesFragmentPresenter = recentMatchesFragment
                        rvSeriesMatches.adapter =
                            typeMatche.seriesMatches?.let { series ->
                                recentMatchesFragment?.let {
                                    SeriesMatchesAdapter(
                                        null,
                                        null,
                                        it,
                                        series
                                    )
                                }
                            }
                    }
                }
            }
        }
    }

}