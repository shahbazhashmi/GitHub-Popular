package matrixsystems.datasource.api.network.resource

import matrixsystems.datasource.model.Resource
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

/**
 * Created by Shahbaz Hashmi on 30/09/20.
 */
internal class ResourceResourceCall<T : Any>(proxy: Call<T>) : ResourceCallDelegate<T, Resource<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<Resource<T>>) = proxy.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val code = response.code()
            val result = if (code in 200 until 300) {
                val body = response.body()
                Resource.success(body!!)
            } else {
                Resource.error(response.message())
            }

            callback.onResponse(this@ResourceResourceCall, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val result = Resource.error<T>(t.message)
            callback.onResponse(this@ResourceResourceCall, Response.success(result))
        }
    })

    override fun cloneImpl() = ResourceResourceCall(proxy.clone())
    override fun timeout() = Timeout().timeout(5, TimeUnit.MINUTES)
}