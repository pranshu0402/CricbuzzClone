package com.chaudharylabs.cricbuzzclone.ui.news.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.Story
import com.chaudharylabs.cricbuzzclone.data.model.top_stoires.StoryX
import com.chaudharylabs.cricbuzzclone.databinding.LytCategoryChildItemsBinding

class CategoryChildAdapter(
    private var present: CategoryChildFragment,
    private var list: List<Story>
) :
    RecyclerView.Adapter<CategoryChildAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytCategoryChildItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_category_child_items, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position].story)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytCategoryChildItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(storyX: StoryX?) {
            binding.apply {
                this.presenter = present
                tvStoryTitle.text = storyX?.hline
                storyDetailsId = storyX?.id.toString()
                tvStoryDesc.text = storyX?.intro
                tvStoryTimestamp.text = present.getFormattedTime(storyX?.pubTime?.toLong())

                val imageUrl =
                    "https://www.cricbuzz.com/a/img/v1/595x396/i1/c${storyX?.imageId}/.jpg"
                Glide.with(ivStories.context)
                    .load(imageUrl)
                    .into(ivStories)
            }
        }
    }

}