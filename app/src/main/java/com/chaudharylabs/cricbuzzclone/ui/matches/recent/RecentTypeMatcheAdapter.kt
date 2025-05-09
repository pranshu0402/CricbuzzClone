package com.chaudharylabs.cricbuzzclone.ui.matches.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.matches.TypeMatche
import com.chaudharylabs.cricbuzzclone.databinding.LytRecentTypeMatchesBinding

class RecentTypeMatcheAdapter(
    private var recentMatchesFragment: RecentMatchesFragment,
    private var list: List<TypeMatche>
) :
    RecyclerView.Adapter<RecentTypeMatcheAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytRecentTypeMatchesBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_recent_type_matches, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytRecentTypeMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(typeMatch: TypeMatche?) {
            binding.apply {
                txtMatchType.text = typeMatch?.matchType

                if (typeMatch != null) {
                    this.presenter = recentMatchesFragment
                    rvSeriesMatches.adapter =
                        typeMatch.seriesMatches?.let { series ->
                            RecentSeriesMatchesAdapter(recentMatchesFragment, series)
                        }
                }
            }
        }
    }

}