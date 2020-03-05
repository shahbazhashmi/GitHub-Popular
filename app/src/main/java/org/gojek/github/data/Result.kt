package org.gojek.github.data

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
data class Result<out T>(val status: Status, val data: List<T>?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: List<T>): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String): Result<T> {
            return Result(Status.ERROR, null, message)
        }

        fun <T> loading(): Result<T> {
            return Result(Status.LOADING, null, null)
        }
    }
}