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
) :
    RecyclerView.Adapter<LiveAdapter.ViewHolder>() {

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
                        chData.visibility = View.GONE
                        lytLiveFeed.visibility = View.GONE
                        tvFeed.visibility = View.VISIBLE
                    }

                    "over-break" -> {
                        chData.visibility = View.GONE
                        lytLiveFeed.visibility = View.VISIBLE
                        tvFeed.visibility = View.GONE
                    }

                    "WICKET" -> {
                        chData.visibility = View.VISIBLE
                        lytLiveFeed.visibility = View.VISIBLE
                        chData.text = "W"
                        tvFeed.visibility = View.GONE
                    }

                    "FOUR" -> {
                        chData.visibility = View.VISIBLE
                        lytLiveFeed.visibility = View.VISIBLE
                        chData.text = "4"
                        tvFeed.visibility = View.GONE
                    }

                    "SIX" -> {
                        chData.visibility = View.VISIBLE
                        lytLiveFeed.visibility = View.GONE
                        chData.text = "6"
                        tvFeed.visibility = View.GONE
                    }

                    else -> {

                    }
                }

                if (commentary?.overNumber == null) {
                    tvOver.visibility = View.GONE
                } else {
                    tvOver.visibility = View.VISIBLE
                    tvOver.text = commentary.overNumber.toString()
                }

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