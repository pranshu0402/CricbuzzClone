package com.chaudharylabs.cricbuzzclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.match_details.squads.Bench
import com.chaudharylabs.cricbuzzclone.databinding.LytSquadsRightItemsBinding
import com.chaudharylabs.cricbuzzclone.ui.SquadsFragment
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants

class Staff2Adapter(val presenter: SquadsFragment, private val staff: List<Bench?>) :
    RecyclerView.Adapter<Staff2Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytSquadsRightItemsBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.lyt_squads_right_items,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return staff.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(staff[position])
    }

    inner class ViewHolder(val binding: LytSquadsRightItemsBinding) :
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