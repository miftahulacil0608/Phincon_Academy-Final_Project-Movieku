package com.example.movieku.adapter.schedule

interface ScheduleMovieListener {
    fun onItemScheduleClick(cinema:String, dateWatch:String,timeWatch:String,studio:String)
}