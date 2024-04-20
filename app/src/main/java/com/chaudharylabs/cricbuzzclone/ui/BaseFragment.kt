package com.chaudharylabs.cricbuzzclone.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

open class BaseFragment : Fragment() {

    fun NavController.safeNavigateWithArgs(direction: NavDirections, bundle: Bundle?) {
        currentDestination?.getAction(direction.actionId)?.run {
            navigate(direction.actionId, bundle)
        }
    }

    fun getDateFromMilliseconds(currentTime: Long?): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val format: DateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val calendar = Calendar.getInstance()
        if (currentTime != null) {
            calendar.timeInMillis = currentTime
        }
        val date = sdf.format(calendar.time)

        return if (date == sdf.format(System.currentTimeMillis())) {
            "Today - ${format.format(currentTime)}"
        } else {
            date
        }
    }

    companion object {
        const val TAG = "BaseFragment"
    }

}