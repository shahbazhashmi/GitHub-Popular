package matrixsystems.datasource.api.network.livedata

import androidx.lifecycle.LiveData
import matrixsystems.datasource.api.network.Resource
import matrixsystems.datasource.api.network.toResource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */
/**
 * A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
 * @param <R>
 */
class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<Resource<R>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): LiveData<Resource<R>> {
        return object : LiveData<Resource<R>>() {
            var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(response.toResource())
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(
                                Resource.error(
                                    throwable.message
                                )
                            )
                        }
                    })
                }
            }
        }
    }
}