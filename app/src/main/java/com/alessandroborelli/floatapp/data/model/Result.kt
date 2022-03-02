package com.alessandroborelli.floatapp.data.model

sealed class Result<T> {
    class Loading<T> : Result<T>()
    data class Success<T>(val data: T) : Result<T>()
    data class Failed<T>(val message: String) : Result<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data = data)
        fun <T> failed(message: String) = Failed<T>(message = message)
    }
}