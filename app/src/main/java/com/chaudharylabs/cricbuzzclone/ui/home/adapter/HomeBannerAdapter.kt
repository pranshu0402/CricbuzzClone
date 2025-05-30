package com.chaudharylabs.cricbuzzclone.ui.home.adapter

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
import com.chaudharylabs.cricbuzzclone.ui.home.HomeFragment
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeBannerAdapter(
    private var present: HomeFragment,
    private var list: List<Matche>,
    private val activity: FragmentActivity?
) :
    RecyclerView.Adapter<HomeBannerAdapter.ViewHolder>() {
    private var screenWidth = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // get screen width
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
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
                this.match = matche

                Glide.with(ivTeam1Pic.context)
                    .load("${URL}/c${matche.matchInfo?.team1?.imageId.toString()}/.jpg")
                    .into(ivTeam1Pic)

                Glide.with(ivTeam2Pic.context)
                    .load("$URL/c${matche.matchInfo?.team2?.imageId.toString()}/.jpg")
                    .into(ivTeam2Pic)

                val team1 = matche.matchInfo?.team1?.teamSName.toString()
                val team2 = matche.matchInfo?.team2?.teamSName.toString()

                tvTeam1Name.text = team1
                tvTeam2Name.text = team2

                val l = matche.matchInfo?.startDate?.toLongOrNull()
                if (l != null) {
                    tvStartDate.text = getDateFromMilliseconds(l)
                }

                if (matche.matchInfo?.state == "Stumps") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.VISIBLE
                }

                if (matche.matchInfo?.state == "Delay") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.GONE
                }

                if (matche.matchInfo?.state == "Rain") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.GONE
                }

                if (matche.matchInfo?.state == "Preview") {
                    tvStartDate.visibility = View.VISIBLE
                    tvStatus.visibility = View.GONE
                    lytTeamScore.visibility = View.GONE
                }

                if (matche.matchInfo?.state == "In Progress") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.VISIBLE
                }

                if (matche.matchInfo?.state == "Toss") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.GONE
                }

                if (matche.matchInfo?.state == "Upcoming") {
                    tvStartDate.visibility = View.VISIBLE
                    tvStatus.visibility = View.GONE
                    lytTeamScore.visibility = View.GONE
                }

                if (matche.matchInfo?.state == "Innings Break") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.VISIBLE
                }

                if (matche.matchInfo?.state == "Lunch") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.VISIBLE
                }

                if (matche.matchInfo?.state == "Tea") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.VISIBLE
                }

                if (matche.matchInfo?.state == "Complete") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.VISIBLE

                    tvStatus.setTextColor(
                        AppCompatResources.getColorStateList(
                            tvStatus.context, R.color.primary_light_blue
                        )
                    )
                }

                val team1Overs = matche.matchScore?.team1Score?.inngs1?.overs.toString()
                    .replace("19.6", "20")
                val team2Overs = matche.matchScore?.team2Score?.inngs1?.overs.toString()
                    .replace("19.6", "20")

                if (matche.matchScore?.team1Score?.inngs1?.runs.toString() == "null") {
                    matche.matchScore?.team1Score?.inngs1?.runs = 0
                }

                if (matche.matchScore?.team1Score?.inngs1?.wickets.toString() == "null") {
                    matche.matchScore?.team1Score?.inngs1?.wickets = 0
                }

                if (matche.matchScore?.team1Score?.inngs1?.overs.toString() == "null") {
                    matche.matchScore?.team1Score?.inngs1?.overs = 0.0
                }

                if (matche.matchScore?.team2Score?.inngs1?.runs.toString() == "null") {
                    matche.matchScore?.team2Score?.inngs1?.runs = 0
                }

                if (matche.matchScore?.team2Score?.inngs1?.wickets.toString() == "null") {
                    matche.matchScore?.team2Score?.inngs1?.wickets = 0
                }

                if (matche.matchScore?.team2Score?.inngs1?.overs.toString() == "null") {
                    matche.matchScore?.team2Score?.inngs1?.overs = 0.0
                }

                if (matche.matchInfo?.matchFormat == "TEST") {

                    tvTeam1Score.text = if (matche.matchScore?.team1Score?.inngs2 == null) {
                        "${matche.matchScore?.team1Score?.inngs1?.runs}-${matche.matchScore?.team1Score?.inngs1?.wickets}"
                    } else {
                        "${matche.matchScore.team1Score.inngs1?.runs}-${matche.matchScore.team1Score.inngs1?.wickets} & ${matche.matchScore.team1Score.inngs2.runs}-${matche.matchScore.team1Score.inngs2.wickets}"
                    }

                    tvTeam2Score.text = if (matche.matchScore?.team2Score?.inngs2 == null) {
                        "${matche.matchScore?.team2Score?.inngs1?.runs}-${matche.matchScore?.team2Score?.inngs1?.wickets}"
                    } else {
                        "${matche.matchScore.team2Score.inngs1?.runs}-${matche.matchScore.team2Score.inngs1?.wickets} & ${matche.matchScore.team2Score.inngs2.runs}-${matche.matchScore.team2Score.inngs2.wickets}"
                    }

                } else {

                    val team1Score =
                        "${matche.matchScore?.team1Score?.inngs1?.runs}-${matche.matchScore?.team1Score?.inngs1?.wickets} ($team1Overs)"
                    val team2Score =
                        "${matche.matchScore?.team2Score?.inngs1?.runs}-${matche.matchScore?.team2Score?.inngs1?.wickets} ($team2Overs)"

                    tvTeam1Score.text = team1Score.replace("null-null (null)", "")

                    tvTeam2Score.text = team2Score.replace("null-null (null)", "")

                }

                if (matche.matchInfo?.state == "Complete") {

                    if (matche.matchInfo.stateTitle?.contains(team1) == true) {
                        tvTeam1Score.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam1Score.context, R.color.white
                            )
                        )
                        tvTeam1Name.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam1Name.context, R.color.white
                            )
                        )

                        tvTeam2Score.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam2Score.context, R.color.primary_soft
                            )
                        )
                        tvTeam2Name.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam2Name.context, R.color.primary_soft
                            )
                        )

                    } else {
                        tvTeam1Score.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam1Score.context, R.color.primary_soft
                            )
                        )
                        tvTeam1Name.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam1Name.context, R.color.primary_soft
                            )
                        )

                        tvTeam2Score.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam2Score.context, R.color.white
                            )
                        )
                        tvTeam2Name.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam2Name.context, R.color.white
                            )
                        )
                    }
                } else {

                    if (matche.matchInfo?.currBatTeamId == matche.matchInfo?.team1?.teamId) {
                        tvTeam1Score.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam1Score.context, R.color.white
                            )
                        )
                        tvTeam1Name.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam1Name.context, R.color.white
                            )
                        )

                        tvTeam2Score.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam2Score.context, R.color.primary_soft
                            )
                        )
                        tvTeam2Name.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam2Name.context, R.color.primary_soft
                            )
                        )
                    } else {
                        tvTeam1Score.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam1Score.context, R.color.primary_soft
                            )
                        )
                        tvTeam1Name.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam1Name.context, R.color.primary_soft
                            )
                        )

                        tvTeam2Score.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam2Score.context, R.color.white
                            )
                        )
                        tvTeam2Name.setTextColor(
                            AppCompatResources.getColorStateList(
                                tvTeam2Name.context, R.color.white
                            )
                        )
                    }
                }
            }
        }
    }

    fun getDateFromMilliseconds(currentTime: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val format1: DateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val format2: DateFormat = SimpleDateFormat("EEE, dd MMM • hh:mm a", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTime
        val date = sdf.format(calendar.time)

        return if (date == sdf.format(System.currentTimeMillis())) {
            "Today - ${format1.format(currentTime)}"
        } else {
            format2.format(currentTime)
        }
    }

}