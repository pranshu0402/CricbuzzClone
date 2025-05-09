package com.chaudharylabs.cricbuzzclone.ui.matches.upcoming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaudharylabs.cricbuzzclone.R
import com.chaudharylabs.cricbuzzclone.data.model.matches.Matche
import com.chaudharylabs.cricbuzzclone.databinding.LytUpcomingMatchItemsBinding
import com.chaudharylabs.cricbuzzclone.ui.utils.Constants.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpcomingMatchesAdapter(
    private var upcomingMatchesFragment: UpcomingMatchesFragment,
    private var list: List<Matche>
) : RecyclerView.Adapter<UpcomingMatchesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LytUpcomingMatchItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.lyt_upcoming_match_items, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(var binding: LytUpcomingMatchItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(match: Matche) {
            binding.apply {
                this.presenter = upcomingMatchesFragment

                if (match.matchInfo?.state == "Upcoming" || match.matchInfo?.state == "Preview") {
                    tvStartDate.setTextColor(
                        AppCompatResources.getColorStateList(
                            tvStartDate.context, R.color.primary_brown
                        )
                    )
                }

                this.match = match

                Glide.with(ivTeam1Pic.context)
                    .load("${URL}/c${match.matchInfo?.team1?.imageId.toString()}/.jpg")
                    .into(ivTeam1Pic)

                Glide.with(ivTeam2Pic.context)
                    .load("$URL/c${match.matchInfo?.team2?.imageId.toString()}/.jpg")
                    .into(ivTeam2Pic)

                val team1 = match.matchInfo?.team1?.teamSName.toString()
                val team2 = match.matchInfo?.team2?.teamSName.toString()

                tvTeam1Name.text = team1
                tvTeam2Name.text = team2

                tvTeam1Name.setTextColor(
                    AppCompatResources.getColorStateList(
                        tvTeam1Name.context, R.color.white
                    )
                )

                tvTeam2Name.setTextColor(
                    AppCompatResources.getColorStateList(
                        tvTeam2Name.context, R.color.white
                    )
                )

                tvStatus.text = match.matchInfo?.status

                val l = match.matchInfo?.startDate?.toLongOrNull()
                if (l != null) {
                    tvStartDate.text = getDateFromMilliseconds(l)
                }

                if (match.matchInfo?.state == "Delay") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.GONE
                }

                if (match.matchInfo?.state == "Rain") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.GONE
                }

                if (match.matchInfo?.state == "Preview") {
                    tvStartDate.visibility = View.VISIBLE
                    tvStatus.visibility = View.GONE
                    lytTeamScore.visibility = View.GONE
                }

                if (match.matchInfo?.state == "In Progress") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.VISIBLE
                }

                if (match.matchInfo?.state == "Toss") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.GONE
                }

                if (match.matchInfo?.state == "Upcoming") {
                    tvStartDate.visibility = View.VISIBLE
                    tvStatus.visibility = View.GONE
                    lytTeamScore.visibility = View.GONE
                }

                if (match.matchInfo?.state == "Innings Break") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.VISIBLE
                }

                if (match.matchInfo?.state == "Complete") {
                    tvStartDate.visibility = View.GONE
                    tvStatus.visibility = View.VISIBLE
                    lytTeamScore.visibility = View.VISIBLE
                }

                val team1Overs = match.matchScore?.team1Score?.inngs1?.overs.toString()
                    .replace("19.6", "20")
                val team2Overs = match.matchScore?.team2Score?.inngs1?.overs.toString()
                    .replace("19.6", "20")

                if (match.matchScore?.team1Score?.inngs1?.runs.toString() == "null") {
                    match.matchScore?.team1Score?.inngs1?.runs = 0
                }

                if (match.matchScore?.team1Score?.inngs1?.wickets.toString() == "null") {
                    match.matchScore?.team1Score?.inngs1?.wickets = 0
                }

                if (match.matchScore?.team1Score?.inngs1?.overs.toString() == "null") {
                    match.matchScore?.team1Score?.inngs1?.overs = 0.0
                }

                if (match.matchScore?.team2Score?.inngs1?.runs.toString() == "null") {
                    match.matchScore?.team2Score?.inngs1?.runs = 0
                }

                if (match.matchScore?.team2Score?.inngs1?.wickets.toString() == "null") {
                    match.matchScore?.team2Score?.inngs1?.wickets = 0
                }

                if (match.matchScore?.team2Score?.inngs1?.overs.toString() == "null") {
                    match.matchScore?.team2Score?.inngs1?.overs = 0.0
                }

                val team1Score =
                    "${match.matchScore?.team1Score?.inngs1?.runs}-${match.matchScore?.team1Score?.inngs1?.wickets} ($team1Overs)"
                val team2Score =
                    "${match.matchScore?.team2Score?.inngs1?.runs}-${match.matchScore?.team2Score?.inngs1?.wickets} ($team2Overs)"

                tvTeam1Score.text = team1Score.replace("null-null (null)", "")

                tvTeam2Score.text = team2Score.replace("null-null (null)", "")


                if (match.matchInfo?.stateTitle?.contains(team1) == true) {
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
                }

                if (match.matchInfo?.stateTitle?.contains(team2) == true) {
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
                } else {
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

    companion object {
        private const val TAG = "LiveMatchesAdapter"
    }

}