package com.chaudharylabs.cricbuzzclone.ui.home.matche_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.match_details.scorecard.Bat
import com.chaudharylabs.cricbuzzclone.databinding.LytBattersScorecardItemsBinding
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.ScorecardFragment

class BatterScorecardAdapter(
    private var present: ScorecardFragment,
    private var list: List<Bat?>
) :
    RecyclerView.Adapter<BatterScorecardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytBattersScorecardItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_batters_scorecard_items, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytBattersScorecardItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bat: Bat?) {
            binding.apply {
                this.presenter = present

                binding.apply {

                    if (bat?.isCaptain == true) {
                        tvBatterName.text = "${bat.batName}(c)"
                    } else if (bat?.isKeeper == true) {
                        tvBatterName.text = "${bat.batName}(wk)"
                    } else {
                        tvBatterName.text = bat?.batName
                    }

                    tvBatterRun.text = bat?.runs.toString()
                    tvBatterBall.text = bat?.balls.toString()
                    tvBatter4s.text = bat?.fours.toString()
                    tvBatter6s.text = bat?.sixes.toString()
                    tvBatterSr.text = bat?.strikeRate.toString()
                    tvBowlerName.text = bat?.outDesc
                }
            }
        }
    }

}