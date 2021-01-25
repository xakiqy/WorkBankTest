package com.example.workbanktest.util

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

fun getDateTodayWithoutTime(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, -1)
    val time = calendar.time
    val sdf = SimpleDateFormat("dd.MM.yyyy")

    return sdf.format(time)
}

fun dipToFloat(context: Context, textSize: Int): Float {
    val scale = context.resources.displayMetrics.scaledDensity
    return context.resources.getDimensionPixelSize(textSize) / scale
}

fun getWholeWeekDate(): List<String> {
    val sdf = SimpleDateFormat("dd.MM.yyyy")

    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    calendar.add(Calendar.DATE, -7)
    val weekList = ArrayList<String>()
    for (i in 0..6) {
        weekList.add(sdf.format(calendar.time))
        calendar.add(Calendar.DATE, 1)
    }
    return weekList
}
