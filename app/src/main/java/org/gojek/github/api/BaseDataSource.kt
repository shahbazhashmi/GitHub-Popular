package org.gojek.github.api

import org.gojek.github.AppController
import org.gojek.github.R
import org.gojek.github.data.Result
import retrofit2.Response

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<List<T>>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        return Result.error(AppController.resourses!!.getString(R.string.something_went_wrong))
    }

}