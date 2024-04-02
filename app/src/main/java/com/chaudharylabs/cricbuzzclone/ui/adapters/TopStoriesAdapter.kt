package com.chaudharylabs.cricbuzzclone.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.Story
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.StoryX
import com.chaudharylabs.cricbuzzclone.databinding.LytTopStoriesListBinding
import com.chaudharylabs.cricbuzzclone.ui.HomeFragment

class TopStoriesAdapter(
    private var present: HomeFragment,
    private var list: List<Story>
) :
    RecyclerView.Adapter<TopStoriesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytTopStoriesListBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_top_stories_list, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        if (list[position].story == null) {
            list.drop(position)
        } else {
            viewHolder.bind(list[position].story)
            viewHolder.itemView.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytTopStoriesListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: StoryX?) {
            binding.apply {
                this.presenter = present
                tvStoryTitle.text = story?.hline

                val imageUrl =
                    "https://www.cricbuzz.com/a/img/v1/595x396/i1/c${story?.imageId}/.jpg"
                Glide.with(ivStories.context)
                    .load(imageUrl)
                    .into(ivStories)
            }
        }
    }

}