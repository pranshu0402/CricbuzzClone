package com.chaudharylabs.cricbuzzclone.ui.adapters

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.Matche
import com.chaudharylabs.cricbuzzclone.databinding.LytHomeBannerListBinding
import com.chaudharylabs.cricbuzzclone.ui.HomeFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeBannerAdapter(
    private var present: HomeFragment,
    private var list: List<Matche>,
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
        viewHolder.bind(list[position])
        val itemWidth = screenWidth / 1.2
        val params: ViewGroup.LayoutParams = viewHolder.binding.lytList.layoutParams
        params.width = itemWidth.toInt()
        viewHolder.binding.lytList.layoutParams = params

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(var binding: LytHomeBannerListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(matche: Matche) {
            binding.apply {
                this.presenter = present
                tvMatchNo.text = matche.matchInfo?.matchDesc
                tvSeriesName.text = " - ${matche.matchInfo?.seriesName}"
                tvTeam1Name.text = matche.matchInfo?.team1?.teamName
                tvTeam2Name.text = matche.matchInfo?.team2?.teamName

                if (matche.matchInfo?.state == "Preview") {
                    tvStartDate.visibility = View.VISIBLE
                    tvStatus.visibility = View.GONE
                    val l = matche.matchInfo.startDate?.toLongOrNull()
                    if (l != null) {
                        tvStartDate.text = getDateFromMilliseconds(l)
                    }

                    tvStartDate.setTextColor(
                        AppCompatResources.getColorStateList(
                            tvStartDate.context,
                            R.color.primary_brown
                        )
                    )

                } else {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    tvStatus.text = matche.matchInfo?.status

                    if (matche.matchInfo?.state == "Toss") {
                        tvStatus.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvStartDate.context,
                                R.color.primary_brown
                            )
                        )
                    } else {
                        tvStatus.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvStartDate.context,
                                R.color.primary_light_blue
                            )
                        )
                    }
                }
            }
        }
    }

    fun getDateFromMilliseconds(currentTime: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val format: DateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTime
        val date = sdf.format(calendar.time)

        return if (date == sdf.format(System.currentTimeMillis())) {
            "Today - ${format.format(currentTime)}"
        } else {
            date
        }
    }

}