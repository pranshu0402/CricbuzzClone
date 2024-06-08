package com.chaudharylabs.cricbuzzclone.ui.adapters

import android.content.Context
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
    private val oversFragment: OversFragment
) :
    RecyclerView.Adapter<OversAdapter.ViewHolder>() {

    private var list: List<OverSummary> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OversAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytOversItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_overs_items, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OversAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(var binding: LytOversItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(overSummary: OverSummary) {
            binding.apply {
                present = oversFragment
                tvOverNo.text = "Ov ${overSummary.overNum?.toInt()?.plus(1)}"
                tvRuns.text = "${overSummary.runs} runs"
                val names = "${overSummary.bowlNames?.joinToString(separator = " & ")} to " +
                        "${overSummary.batStrikerNames?.joinToString(separator = " & ")}"

                if (overSummary.batNonStrikerNames.isNullOrEmpty()) {
                    tvName.text = names
                } else {
                    tvName.text =
                        "$names&${overSummary.batNonStrikerNames?.joinToString(separator = " & ")}"
                }

                stringToWords(overSummary.o_summary.toString()).let {
                    it?.forEach { d ->
                        binding.chOver.addView(createTagChip(chOver.context, d))
                    }
                }
            }
        }
    }

    fun notifyList(overSummaryList: List<OverSummary>) {
        list = overSummaryList
        notifyDataSetChanged()
    }

    private fun createTagChip(context: Context, chipName: String): Chip {
        return Chip(context).apply {
            text = chipName
            chipCornerRadius = 50f
            setChipBackgroundColorResource(R.color.white)
            setTextColor(context.resources.getColor(R.color.black))
            chipStartPadding = 2f
            chipEndPadding = 2f
            setEnsureMinTouchTargetSize(false)
        }

    }

    fun stringToWords(s: String?) = s?.trim()?.splitToSequence(' ')
        ?.filter { it.isNotEmpty() }
        ?.toList()
}