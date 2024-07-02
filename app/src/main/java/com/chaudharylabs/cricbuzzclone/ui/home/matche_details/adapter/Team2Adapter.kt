package com.chaudharylabs.cricbuzzclone.ui.home.matche_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.match_details.squads.PlayingXI
import com.chaudharylabs.cricbuzzclone.databinding.LytSquadsRightItemsBinding
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.SquadsFragment
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants

class Team2Adapter(val presenter: SquadsFragment, private val playingXI: List<PlayingXI?>) :
    RecyclerView.Adapter<Team2Adapter.ViewHolder>() {
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
        return playingXI.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(playingXI[position])
    }

    inner class ViewHolder(val binding: LytSquadsRightItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playingXI: PlayingXI?) {
            binding.apply {
                this.present = presenter
                Glide.with(ivProfile.context)
                    .load("${Constants.PROFILE_URL}/c${playingXI?.faceImageId?.toString()}/.jpg")
                    .into(ivProfile)
                tvName.text = playingXI?.name
                tvSkill.text = playingXI?.role
            }
        }
    }
}