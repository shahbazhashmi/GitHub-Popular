package org.github.popular.repository.api.network

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
        withContext(Dispatchers.Main) { result.value =
            Resource.loading()
        }
        CoroutineScope(coroutineContext).launch(supervisorJob) {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                try {
                    fetchFromNetwork()
                } catch (e: Exception) {
                    Log.e(TAG, "An error happened: $e")
                    setValue(Resource.error(e.localizedMessage))
                }
            } else {
                Log.d(TAG, "Return data from local database")
                setValue(Resource.success(dbResult))
            }
        }
        return this
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    // ---

    private suspend fun fetchFromNetwork() {
        Log.d(NetworkAndDBBoundResource::class.java.name, "Fetch data from network")
        val apiResponse = createCall()
        Log.e(NetworkAndDBBoundResource::class.java.name, "Data fetched from network")
        saveCallResults(processResponse(apiResponse))
        setValue(Resource.success(loadFromDb()))
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        Log.d(NetworkAndDBBoundResource::class.java.name, "Resource: "+newValue)
        if (result.value != newValue) result.postValue(newValue)
    }

    @WorkerThread
    protected fun processResponse(response: Resource<RequestType>): RequestType? = response.data

    @WorkerThread
    protected abstract suspend fun saveCallResults(items: RequestType?)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType

    @MainThread
    protected abstract suspend fun createCall(): Resource<RequestType>

   /* *//**
     * The final result LiveData
     *//*
    private val result = MediatorLiveData<Resource<ResultType?>>()

    init {
        // Send loading state to UI
        result.value = Resource.loading()

        val dbSource = this.loadFromDb()

        result.addSource(dbSource) { data ->

            result.removeSource(dbSource) // Once done data loading remove source

            if (mustFetch(data)) {

                fetchFromNetwork(dbSource, true)

            } else if (shouldFetch(data)) {

                fetchFromNetwork(dbSource, false)

            } else {

                result.addSource(dbSource) { newData -> setValue(Resource.success(newData)) }
            }
        }
    }

    *//**
     * Fetch the data from network and persist into DB and then
     * send it back to UI.
     *//*
    private fun fetchFromNetwork(dbSource: LiveData<ResultType>, mustFetch: Boolean) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) {
            result.setValue(Resource.loading())
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(dbSource)
            result.removeSource(apiResponse)

            response?.also { data ->
                if (data.status.isSuccessful()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        processResponse(data)?.let { requestType ->
                            saveCallResult(requestType)
                        }
                        withContext(Dispatchers.Main) {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                } else {
                    onFetchFailed()
                    result.addSource(if (mustFetch) apiResponse else dbSource) {
                        result.setValue(Resource.error(data.errorMessage))
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType?>) {
        if (result.value != newValue) result.value = newValue
    }

    protected fun onFetchFailed() {}

    fun asLiveData(): LiveData<Resource<ResultType?>> {
        return result
    }

    @WorkerThread
    private fun processResponse(response: Resource<RequestType>): RequestType? {
        return response.data
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    *//**
     * prefers network call and does not return network call errors
     *//*
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    *//**
     * prefers network call and not returns network call errors
     *//*
    @MainThread
    protected abstract fun mustFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<Resource<RequestType>>*/
}