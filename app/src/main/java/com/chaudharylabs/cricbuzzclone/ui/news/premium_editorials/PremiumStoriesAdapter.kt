package com.chaudharylabs.cricbuzzclone.ui.news.premium_editorials

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.Story
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.StoryX
import com.chaudharylabs.cricbuzzclone.databinding.LytPremiumStoriesItemsBinding

class PremiumStoriesAdapter(
    private var present: PremiumNewsFragment,
    private val list: List<Story>
) :
    RecyclerView.Adapter<PremiumStoriesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytPremiumStoriesItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_premium_stories_items, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position].story)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytPremiumStoriesItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: StoryX?) {
            binding.apply {
                this.presenter = present
                tvStoryTitle.text = story?.hline
                storyDetailsId = story?.id.toString()

                tvStoryDesc.text = story?.intro

                val imageUrl =
                    "https://www.cricbuzz.com/a/img/v1/595x396/i1/c${story?.imageId}/.jpg"
                Glide.with(ivStories.context)
                    .load(imageUrl)
                    .into(ivStories)

                tvStoryTimestamp.text = present.getFormattedTime(story?.pubTime?.toLong())
            }
        }
    }

}