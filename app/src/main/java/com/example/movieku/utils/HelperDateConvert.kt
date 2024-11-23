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

    fun getMontAndYear():String{
        val formatter = SimpleDateFormat("MMM-yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance().time
        return formatter.format(calendar)
    }

    fun generateDates():List<Date>{
        val calendar = Calendar.getInstance()
        val dateList = mutableListOf<Date>()

        repeat(8){
            dateList.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return dateList
    }
}