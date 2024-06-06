package com.chaudharylabs.cricbuzzclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.match_details.overs.OverSummary
import com.chaudharylabs.cricbuzzclone.databinding.LytOversItemsBinding
import com.chaudharylabs.cricbuzzclone.ui.OversFragment
import com.google.android.material.chip.Chip


class OversAdapter(
    private val oversFragment: OversFragment,
    private val overSummaryList: List<OverSummary>
) :
    RecyclerView.Adapter<OversAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OversAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytOversItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_overs_items, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OversAdapter.ViewHolder, position: Int) {
        holder.bind(overSummaryList[position])
    }

    override fun getItemCount(): Int {
        return overSummaryList.size
    }

    inner class ViewHolder(var binding: LytOversItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(overSummary: OverSummary) {
            binding.apply {
                present = oversFragment
                tvOverNo.text = "Ov ${overSummary.overNum?.toInt()?.plus(1)}"
                tvRuns.text = overSummary.runs.toString()
                val names = "${overSummary.bowlNames?.joinToString(separator = "&")}to" +
                        "${overSummary.batStrikerNames?.joinToString(separator = "&")}"

                if (overSummary.batNonStrikerNames.isNullOrEmpty()) {
                    tvName.text = names
                } else {
                    tvName.text =
                        "$names&${overSummary.batNonStrikerNames?.joinToString(separator = "&")}"
                }

                val x = stringToWords(overSummary.o_summary.toString())
                println(x)

                x.let {
                    it?.forEach { _ ->
                        val chip = Chip(chOver.context)
                        chip.text = overSummary.o_summary
                        chip.chipCornerRadius = 50f
                        chip.setChipBackgroundColorResource(R.color.white)
                        chip.setTextColor(chOver.context.resources.getColor(R.color.black))
                        chOver.addView(chip)
                    }
                }
            }
        }


    }

    fun stringToWords(s: String?) = s?.trim()?.splitToSequence(' ')
        ?.filter { it.isNotEmpty() } // or: .filter { it.isNotBlank() }
        ?.toList()
}