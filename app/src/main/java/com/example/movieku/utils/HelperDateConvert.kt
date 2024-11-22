package com.example.movieku.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object HelperDateConvert {

    private fun Date.toReleaseDate():String{
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(this)
    }

    fun releaseDateGte(rangeDay:Int):String{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, rangeDay)
        val currentDateTime = calendar.time
        return currentDateTime.toReleaseDate()
    }
     fun releaseDateLte():String{
        val currentDateTime = Calendar.getInstance().time
        return currentDateTime.toReleaseDate()
    }
}