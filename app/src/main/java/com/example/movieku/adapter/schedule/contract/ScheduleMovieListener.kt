package com.example.movieku.adapter.schedule.contract

interface ScheduleMovieListener {
    fun onItemScheduleClick(cinema:String, dateWatch:String,timeWatch:String,studio:String)
}