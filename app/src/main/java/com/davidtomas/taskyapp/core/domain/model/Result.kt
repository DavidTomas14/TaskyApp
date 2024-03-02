package com.davidtomas.taskyapp.core.domain.model

sealed class Result<T, R : Failure>(val data: T? = null, val error: R? = null) {
    class Success<T, R : Failure>(data: T?) : Result<T, R>(data, null)
    class Error<T, R : Failure>(error: R?) : Result<T, R>(data = null, error = error)

    fun fold(
        onError: (R) -> Any,
        onSuccess: (T) -> Any
    ): Any =
        when (this) {
            is Error -> onError(error!!)
            is Success -> onSuccess(data!!)
        }
}

fun <T, R : Failure> Result<T, R>.asEmptyDataResult(): EmptyDataResult {
    return when (this) {
        is Result.Error -> Result.Error(null)
        is Result.Success -> Result.Success(null)
    }
}

inline fun <T, R : Failure, S> Result<T, R>.map(map: (T) -> S): Result<S, R> {
    return when (this) {
        is Result.Error -> Result.Error(null)
        is Result.Success -> Result.Success(data = map(data!!))
    }
}

typealias EmptyDataResult = Result<Unit, Failure>
