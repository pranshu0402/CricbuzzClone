package com.chaudharylabs.cricbuzzclone.ui.adapters

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.TypeMatche
import com.chaudharylabs.cricbuzzclone.databinding.LytHomeBannerListBinding
import com.chaudharylabs.cricbuzzclone.ui.HomeFragment

class HomeBannerAdapter(
    private var presenter: HomeFragment?,
    private val matches: List<TypeMatche>,
    private val filters: List<String>,
    private val activity: FragmentActivity
) :
    RecyclerView.Adapter<HomeBannerAdapter.ViewHolder>() {
    private var screenWidth = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // get screen width
        val displayMetrics = DisplayMetrics()
        activity.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels

        val inflater = LayoutInflater.from(parent.context)
        val binding: LytHomeBannerListBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_home_banner_list, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(matches[position])
        val itemWidth = screenWidth / 1.2
        val params: ViewGroup.LayoutParams = viewHolder.binding.lytList.layoutParams
        params.width = itemWidth.toInt()
        viewHolder.binding.lytList.layoutParams = params

    }

    override fun getItemCount(): Int {
        return matches.size
    }

    inner class ViewHolder(var binding: LytHomeBannerListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(typeMatche: TypeMatche) {
            binding.presenter = presenter
            typeMatche.seriesMatches?.get(1)?.seriesAdWrapper?.matches?.get(0)?.matchInfo?.status
        }
    }

}