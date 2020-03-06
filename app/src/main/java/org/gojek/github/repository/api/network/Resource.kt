package org.gojek.github.repository.api.network

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<ResultType>(
    var status: Status,
    var data: ResultType? = null,
    var errorMessage: String? = null
) {

    companion object {
        /**
         * Creates [Resource] object with `SUCCESS` status and [data].
         * Returning object of Resource(Status.SUCCESS, data, null)  last value is null so passing it optionally
         */
        fun <ResultType> success(data: ResultType): Resource<ResultType> =
            Resource(Status.SUCCESS, data)

        /**
         * Creates [Resource] object with `LOADING` status to notify
         * the UI to showing loading.
         * Returning object of Resource(Status.SUCCESS, null, null) last two values are null so passing them optionally
         */
        fun <ResultType> loading(): Resource<ResultType> = Resource(Status.LOADING)

        /**
         * Creates [Resource] object with `ERROR` status and [message].
         * Returning object of Resource(Status.ERROR, errorMessage = message)
         */
        fun <ResultType> error(message: String?): Resource<ResultType> =
            Resource(Status.ERROR, errorMessage = message)

    }
}