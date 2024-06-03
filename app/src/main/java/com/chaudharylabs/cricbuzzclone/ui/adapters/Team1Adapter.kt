package com.chaudharylabs.cricbuzzclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.match_details.squads.PlayingXI
import com.chaudharylabs.cricbuzzclone.databinding.LytSquadsLeftItemsBinding
import com.chaudharylabs.cricbuzzclone.ui.SquadsFragment
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants

class Team1Adapter(
    private val presenter: SquadsFragment,
    private val playingXI: List<PlayingXI?>
) :
    RecyclerView.Adapter<Team1Adapter.ViewHolder>() {
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
        return playingXI.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(playingXI[position])
    }

    inner class ViewHolder(private val binding: LytSquadsLeftItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playingXI: PlayingXI?) {
            binding.apply {
                present = presenter
                Glide.with(ivProfile.context)
                    .load("${Constants.PROFILE_URL}/c${playingXI?.faceImageId?.toString()}/.jpg")
                    .into(ivProfile)
                tvName.text = playingXI?.name
                tvSkill.text = playingXI?.role
            }
        }
    }
}