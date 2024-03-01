package com.davidtomas.taskyapp.core.domain.util

sealed class Result<T>(val data: T? = null, val error: Failure? = null) {
    class Success<T>(data: T?) : Result<T>(data, null)
    class Error<T>(error: Failure) : Result<T>(data = null, error = error)

    fun fold(
        fnL: (Failure) -> Any,
        fnR: (T) -> Any
    ): Any =
        when (this) {
            is Error -> fnL(error!!)
            is Success -> fnR(data!!)
        }
}

fun <T> Result<T>.asEmptyDataResult(): EmptyDataResult {
    return when (this) {
        is Result.Error -> Result.Error(error!!)
        is Result.Success -> Result.Success(null)
    }
}

inline fun <T, R> Result<T>.map(map: (T) -> R): Result<R> {
    return when (this) {
        is Result.Error -> Result.Error(error!!)
        is Result.Success -> Result.Success(data = map(data!!))
    }
}

typealias EmptyDataResult = Result<Unit>
