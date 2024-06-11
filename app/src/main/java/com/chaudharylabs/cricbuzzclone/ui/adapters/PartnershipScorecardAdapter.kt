package com.chaudharylabs.cricbuzzclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard.Partnerships
import com.chaudharylabs.cricbuzzclone.databinding.LytPartnershipScorecardItemsBinding
import com.chaudharylabs.cricbuzzclone.ui.ScorecardFragment

class PartnershipScorecardAdapter(
    private var present: ScorecardFragment,
    private var list: ArrayList<Partnerships?>
) :
    RecyclerView.Adapter<PartnershipScorecardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytPartnershipScorecardItemsBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.lyt_partnership_scorecard_items,
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

    inner class ViewHolder(private var binding: LytPartnershipScorecardItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(partnerships: Partnerships?) {
            binding.apply {
                this.presenter = present
                partnership = partnerships
            }
        }
    }

}