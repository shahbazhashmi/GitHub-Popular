package matrixsystems.datasource.api.network

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 *
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkAndDBBoundResource<ResultType, RequestType> {

    private val TAG = "NetworkAndDBBoundResource"

    private val result = MutableLiveData<Resource<ResultType>>()
    private val supervisorJob = SupervisorJob()

    suspend fun build(): NetworkAndDBBoundResource<ResultType, RequestType> {
        withContext(Dispatchers.Main) {
            result.value =
                Resource.loading()
        }
        CoroutineScope(coroutineContext).launch(supervisorJob) {
            handleData()
        }
        return this
    }

    private suspend fun handleData() {
        val dbResult = loadFromDb()
        if (mustFetch() || shouldFetch(dbResult)) {
            try {
                val apiResponse = fetchFromNetwork()
                if (apiResponse.status.isSuccessful()) {
                    saveCallResults(processResponse(apiResponse))
                    /**
                     * publish new data
                     */
                    setValue(Resource.success(loadFromDb()))
                } else {
                    throw Exception(apiResponse.errorMessage ?: "API Exception Occurred")
                }
            } catch (e: Exception) {
                Log.e(TAG, "An error happened: $e")
                if (mustFetch() || !isDataAvailable(dbResult)) {
                    setValue(Resource.error(e.message ?: e.toString()))
                } else {
                    setValue(Resource.success(dbResult))
                }
            }
        } else {
            Log.d(TAG, "Return data from local database")
            setValue(Resource.success(dbResult))
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>


    private suspend fun fetchFromNetwork(): Resource<RequestType> {
        Log.d(TAG, "Fetch data from network")
        val apiResponse = createCall()
        Log.e(TAG, "Data fetched from network")
        return apiResponse
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        Log.d(TAG, "Resource: "+newValue)
        if (result.value != newValue) result.postValue(newValue)
    }

    @WorkerThread
    protected fun processResponse(response: Resource<RequestType>): RequestType? = response.data

    @WorkerThread
    protected abstract suspend fun saveCallResults(items: RequestType?)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun mustFetch(): Boolean

    @MainThread
    protected abstract fun isDataAvailable(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType

    @MainThread
    protected abstract suspend fun createCall(): Resource<RequestType>

}