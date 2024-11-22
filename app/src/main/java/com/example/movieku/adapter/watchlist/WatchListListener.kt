package com.example.movieku.adapter.watchlist

interface WatchListListener {
    fun onClickDelete(movieId:Int)
    fun onClickItem(movieId: Int)
}