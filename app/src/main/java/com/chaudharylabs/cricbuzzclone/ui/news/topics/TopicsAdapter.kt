package com.chaudharylabs.cricbuzzclone.ui.news.topics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.news.topics.TopicsResponse
import com.chaudharylabs.cricbuzzclone.databinding.LytTopicsItemsBinding

class TopicsAdapter(
    private var present: TopicsFragment,
    private var list: List<TopicsResponse.Topic>
) :
    RecyclerView.Adapter<TopicsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytTopicsItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_topics_items, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytTopicsItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: TopicsResponse.Topic) {
            binding.apply {
                this.presenter = present
                tvStoryTitle.text = topic.headline
                story = topic
                tvStoryDesc.text = topic.description
            }
        }
    }

}