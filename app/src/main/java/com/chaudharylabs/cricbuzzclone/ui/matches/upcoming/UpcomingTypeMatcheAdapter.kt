package com.chaudharylabs.cricbuzzclone.ui.matches.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.matches.TypeMatche
import com.chaudharylabs.cricbuzzclone.databinding.LytUpcomingTypeMatchesBinding

class UpcomingTypeMatcheAdapter(
    private var upcomingMatchesFragment: UpcomingMatchesFragment,
    private var list: List<TypeMatche>
) :
    RecyclerView.Adapter<UpcomingTypeMatcheAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytUpcomingTypeMatchesBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_upcoming_type_matches, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytUpcomingTypeMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(typeMatch: TypeMatche?) {
            binding.apply {
                txtMatchType.text = typeMatch?.matchType

                if (typeMatch != null) {
                    this.presenter = upcomingMatchesFragment
                    rvSeriesMatches.adapter =
                        typeMatch.seriesMatches?.let { series ->
                            UpcomingSeriesMatchesAdapter(upcomingMatchesFragment, series)
                        }
                }
            }
        }
    }

}