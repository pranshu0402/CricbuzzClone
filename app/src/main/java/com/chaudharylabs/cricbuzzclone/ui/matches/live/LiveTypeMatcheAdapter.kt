package com.chaudharylabs.cricbuzzclone.ui.matches.live

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.matches.TypeMatche
import com.chaudharylabs.cricbuzzclone.databinding.LytLiveTypeMatchesBinding

class LiveTypeMatcheAdapter(
    private var liveMatchesFragment: LiveMatchesFragment,
    private var list: List<TypeMatche>
) :
    RecyclerView.Adapter<LiveTypeMatcheAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytLiveTypeMatchesBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_live_type_matches, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytLiveTypeMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(typeMatche: TypeMatche?) {
            binding.apply {
                txtMatchType.text = typeMatche?.matchType

                if (typeMatche != null) {
                    this.liveMatchesFragmentPresenter = liveMatchesFragment
                    rvSeriesMatches.adapter =
                        typeMatche.seriesMatches?.let { series ->
                            LiveSeriesMatchesAdapter(liveMatchesFragment, series)
                        }
                }
            }
        }
    }

}