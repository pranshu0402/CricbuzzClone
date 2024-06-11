package com.chaudharylabs.cricbuzzclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard.Bowl
import com.chaudharylabs.cricbuzzclone.databinding.LytBowlersScorecardItemsBinding
import com.chaudharylabs.cricbuzzclone.ui.ScorecardFragment

class BowlerScorecardAdapter(
    private var present: ScorecardFragment,
    private var list: ArrayList<Bowl?>
) :
    RecyclerView.Adapter<BowlerScorecardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytBowlersScorecardItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_bowlers_scorecard_items, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytBowlersScorecardItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bowl: Bowl?) {
            binding.apply {
                this.presenter = present
                bowler = bowl
            }
        }
    }

}