package com.chaudharylabs.cricbuzzclone.ui.home.matche_details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.match_details.live.LiveResponse
import com.chaudharylabs.cricbuzzclone.databinding.LytLiveCommentaryItemsBinding
import com.chaudharylabs.cricbuzzclone.ui.home.matche_details.LiveFragment


class LiveAdapter(
    private val oversFragment: LiveFragment,
    private val commentaryList: List<LiveResponse.Commentary?>,
    private val commentarySnippetList: List<LiveResponse.CommentarySnippet?>
) : RecyclerView.Adapter<LiveAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytLiveCommentaryItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_live_commentary_items, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commentaryList[position])
    }

    override fun getItemCount(): Int {
        return commentaryList.size
    }

    inner class ViewHolder(var binding: LytLiveCommentaryItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(commentary: LiveResponse.Commentary?) {
            binding.apply {
                present = oversFragment

                when (commentary?.event) {
                    "NONE" -> {
                        lytLiveFeed.visibility = View.GONE
                        tvFeed.visibility = View.VISIBLE
                    }

                    "DROPPED" -> {
                        lytCircle.visibility = View.GONE
                        lytLiveFeed.visibility = View.VISIBLE
                        tvFeed.visibility = View.GONE
                    }

                    "over-break" -> {
                        lytCircle.visibility = View.GONE
                        lytLiveFeed.visibility = View.VISIBLE
                        tvFeed.visibility = View.GONE
                    }

                    "over-break,WICKET" -> {
                        lytCircle.visibility = View.VISIBLE
                        lytLiveFeed.visibility = View.VISIBLE
                        tvData.text = "W"
                        ivData.setBackgroundResource(R.color.primary_red)
                        tvFeed.visibility = View.GONE
                    }

                    "over-break,FOUR" -> {
                        lytCircle.visibility = View.VISIBLE
                        lytLiveFeed.visibility = View.VISIBLE
                        tvData.text = "4"
                        ivData.setBackgroundResource(R.color.primary_light_blue)
                        tvFeed.visibility = View.GONE
                    }

                    "over-break,SIX" -> {
                        lytCircle.visibility = View.VISIBLE
                        lytLiveFeed.visibility = View.VISIBLE
                        tvData.text = "6"
                        ivData.setBackgroundResource(R.color.primary_purple)
                        tvFeed.visibility = View.GONE
                    }

                    "WICKET" -> {
                        lytCircle.visibility = View.VISIBLE
                        lytLiveFeed.visibility = View.VISIBLE
                        tvData.text = "W"
                        ivData.setBackgroundResource(R.color.primary_red)
                        tvFeed.visibility = View.GONE
                    }

                    "FOUR" -> {
                        lytCircle.visibility = View.VISIBLE
                        lytLiveFeed.visibility = View.VISIBLE
                        tvData.text = "4"
                        ivData.setBackgroundResource(R.color.primary_light_blue)
                        tvFeed.visibility = View.GONE
                    }

                    "SIX" -> {
                        lytCircle.visibility = View.VISIBLE
                        lytLiveFeed.visibility = View.VISIBLE
                        tvData.text = "6"
                        ivData.setBackgroundResource(R.color.primary_purple)
                        tvFeed.visibility = View.GONE
                    }

                    "FIFTY" -> {
                        lytCircle.visibility = View.VISIBLE
                        lytLiveFeed.visibility = View.VISIBLE
                        tvData.text = "F"
                        ivData.setBackgroundResource(R.color.primary_brown)
                        tvFeed.visibility = View.GONE
                    }

                    "CENTURY" -> {
                        lytCircle.visibility = View.VISIBLE
                        lytLiveFeed.visibility = View.VISIBLE
                        tvData.text = "C"
                        ivData.setBackgroundResource(R.color.secondary_green)
                        tvFeed.visibility = View.GONE
                    }

                    else -> {

                    }
                }

                tvOver.text = commentary?.overNumber.toString()

                if (commentary?.commentaryFormats?.bold?.formatId?.isNotEmpty() == true) {

                    if (commentary.commentaryFormats.bold.formatValue?.isNotEmpty() == true) {

                        commentary.commentaryFormats.bold.formatId.forEach { formatId ->

                            commentary.commentaryFormats.bold.formatValue.forEach { formatValue ->

                                if (commentary.commText?.contains(formatId.toString()) == true) {

                                    tvLiveFeed.text =
                                        commentary.commText.replace("$formatId", "$formatValue")
                                            .replace("\\n", "\n")
                                    tvFeed.text =
                                        commentary.commText.replace("$formatId", "$formatValue")
                                            .replace("\\n", "\n")
                                }

                            }
                        }
                    }
                } else {
                    tvLiveFeed.text = commentary?.commText?.replace("\\n", "\n")
                    tvFeed.text = commentary?.commText?.replace("\\n", "\n")
                }
            }
        }
    }
}