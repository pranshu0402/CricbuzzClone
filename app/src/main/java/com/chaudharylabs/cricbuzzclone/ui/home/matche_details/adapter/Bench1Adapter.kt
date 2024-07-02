package com.chaudharylabs.cricbuzzclone.ui.home.matche_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.match_details.squads.Bench
import com.chaudharylabs.cricbuzzclone.databinding.LytSquadsLeftItemsBinding
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.SquadsFragment
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants

class Bench1Adapter(val presenter: SquadsFragment, private val bench: List<Bench?>) :
    RecyclerView.Adapter<Bench1Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytSquadsLeftItemsBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.lyt_squads_left_items,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return bench.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bench[position])
    }

    inner class ViewHolder(val binding: LytSquadsLeftItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bench: Bench?) {
            binding.apply {
                this.present = presenter
                Glide.with(ivProfile.context)
                    .load("${Constants.PROFILE_URL}/c${bench?.faceImageId?.toString()}/.jpg")
                    .into(ivProfile)
                tvName.text = bench?.name
                tvSkill.text = bench?.role
            }
        }
    }
}