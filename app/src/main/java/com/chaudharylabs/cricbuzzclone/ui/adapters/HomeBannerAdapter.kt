package com.chaudharylabs.cricbuzzclone.ui.adapters

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.matches.Matche
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
    private val url = "https://static.cricbuzz.com/a/img/v1/25x18/i1"

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

                Glide.with(ivTeam1Pic.context)
                    .load("$url/c${matche.matchInfo?.team1?.imageId.toString()}/.jpg")
                    .into(ivTeam1Pic)

                Glide.with(ivTeam2Pic.context)
                    .load("$url/c${matche.matchInfo?.team2?.imageId.toString()}/.jpg")
                    .into(ivTeam2Pic)

                if (matche.matchInfo?.state == "Preview") {
                    tvStartDate.visibility = View.VISIBLE
                    tvStatus.visibility = View.GONE
                    lytTeamScore.visibility = View.GONE

                    tvTeam1Name.text = matche.matchInfo.team1?.teamName
                    tvTeam2Name.text = matche.matchInfo.team2?.teamName

                    tvTeam1Name.setTextColor(
                        AppCompatResources.getColorStateList(
                            tvStartDate.context,
                            R.color.white
                        )
                    )

                    tvTeam2Name.setTextColor(
                        AppCompatResources.getColorStateList(
                            tvStartDate.context,
                            R.color.white
                        )
                    )

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
                    lytTeamScore.visibility = View.VISIBLE
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

                    if (matche.matchInfo?.state == "Complete") {

                        tvTeam1Name.text = matche.matchInfo.team1?.teamSName
                        tvTeam2Name.text = matche.matchInfo.team2?.teamSName

                        val team1 = matche.matchInfo.team1?.teamSName.toString()
                        val team2 = matche.matchInfo.team2?.teamSName.toString()

                        val team1Overs = matche.matchScore?.team1Score?.inngs1?.overs.toString()
                            .replace("19.6", "20")
                        val team2Overs = matche.matchScore?.team2Score?.inngs1?.overs.toString()
                            .replace("19.6", "20")

                        val team1Score =
                            "${matche.matchScore?.team1Score?.inngs1?.runs}-${matche.matchScore?.team1Score?.inngs1?.wickets} ($team1Overs)"
                        val team2Score =
                            "${matche.matchScore?.team2Score?.inngs1?.runs}-${matche.matchScore?.team2Score?.inngs1?.wickets} ($team2Overs)"

                        tvTeam1Score.text = team1Score.replace("null-null (null)", "")

                        tvTeam2Score.text = team2Score.replace("null-null (null)", "")


                        if (matche.matchInfo.stateTitle?.contains(team1) == true) {
                            tvTeam1Score.setTextColor(
                                AppCompatResources.getColorStateList(
                                    tvStartDate.context,
                                    R.color.white
                                )
                            )
                            tvTeam1Name.setTextColor(
                                AppCompatResources.getColorStateList(
                                    tvStartDate.context,
                                    R.color.white
                                )
                            )
                        } else {
                            tvTeam1Score.setTextColor(
                                AppCompatResources.getColorStateList(
                                    tvStartDate.context,
                                    R.color.primary_soft
                                )
                            )
                            tvTeam1Name.setTextColor(
                                AppCompatResources.getColorStateList(
                                    tvStartDate.context,
                                    R.color.primary_soft
                                )
                            )
                        }

                        if (matche.matchInfo.stateTitle?.contains(team2) == true) {
                            tvTeam2Score.setTextColor(
                                AppCompatResources.getColorStateList(
                                    tvStartDate.context,
                                    R.color.white
                                )
                            )
                            tvTeam2Name.setTextColor(
                                AppCompatResources.getColorStateList(
                                    tvStartDate.context,
                                    R.color.white
                                )
                            )
                        } else {
                            tvTeam2Score.setTextColor(
                                AppCompatResources.getColorStateList(
                                    tvStartDate.context,
                                    R.color.primary_soft
                                )
                            )
                            tvTeam2Name.setTextColor(
                                AppCompatResources.getColorStateList(
                                    tvStartDate.context,
                                    R.color.primary_soft
                                )
                            )
                        }

                    } else {
                        tvTeam1Name.text = matche.matchInfo?.team1?.teamName
                        tvTeam2Name.text = matche.matchInfo?.team2?.teamName
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