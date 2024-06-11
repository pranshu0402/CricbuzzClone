package com.chaudharylabs.cricbuzzclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard.Wkt
import com.chaudharylabs.cricbuzzclone.databinding.LytFallofwicketsScorecardItemsBinding
import com.chaudharylabs.cricbuzzclone.ui.ScorecardFragment

class FallOfWicketsScorecardAdapter(
    private var present: ScorecardFragment,
    private var list: ArrayList<Wkt?>
) :
    RecyclerView.Adapter<FallOfWicketsScorecardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytFallofwicketsScorecardItemsBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.lyt_fallofwickets_scorecard_items,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytFallofwicketsScorecardItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wkt: Wkt?) {
            binding.apply {
                this.presenter = present
                wickets = wkt
            }
        }
    }

}