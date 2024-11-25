package com.test.applydigital.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.test.applydigital.R
import java.time.Instant
import java.time.Duration

fun String.toTimeAgo(): String {
    val date = Instant.parse(this)

    val now = Instant.now()

    val duration = Duration.between(date, now)

    return when {
        duration.toDays() > 0 -> {
            if (duration.toDays() == 1L) "Yesterday" else "${duration.toDays()} Days ago"
        }
        duration.toHours() > 0 -> {
            if (duration.toHours() == 1L) "1 hour ago" else "${duration.toHours()} hours"
        }
        duration.toMinutes() > 0 -> {
            if (duration.toMinutes() == 1L) "1 minute ago" else "${duration.toMinutes()} minutes ago"
        }
        else -> "Just now"
    }
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

@Composable
fun Context.showNoInternetToast() {
    Toast.makeText(this, stringResource(id = R.string.no_internet), Toast.LENGTH_SHORT).show()
}