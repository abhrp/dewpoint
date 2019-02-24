package com.github.abhrp.dewpoint.util

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class DateUtil @Inject constructor() {

    fun getFormattedDate(date: Long): String {
        val now  = Date(date * 1000)
        val simpleDateFormat = SimpleDateFormat("MMMM dd, hh:mm a")
        return simpleDateFormat.format(now)
    }

}