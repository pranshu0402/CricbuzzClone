package com.chaudharylabs.cricbuzzclone.ui.news.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.news.categories.CategoryResponse
import com.chaudharylabs.cricbuzzclone.databinding.LytCategoriesItemsBinding

class CategoryAdapter(
    private var present: CategoriesFragment,
    private var list: List<CategoryResponse.StoryType>
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytCategoriesItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_categories_items, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var binding: LytCategoriesItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(storyType: CategoryResponse.StoryType) {
            binding.apply {
                this.presenter = present
                tvStoryTitle.text = storyType.name
                story = storyType
                tvStoryDesc.text = storyType.description
            }
        }
    }

}