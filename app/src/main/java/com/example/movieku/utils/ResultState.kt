package com.example.movieku.utils

sealed class ResultState<out T> {
    data object Loading:ResultState<Nothing>()
    data class Success<out T>(val data: T):ResultState<T>()
    data class Error(val message:Throwable):ResultState<Nothing>()
}