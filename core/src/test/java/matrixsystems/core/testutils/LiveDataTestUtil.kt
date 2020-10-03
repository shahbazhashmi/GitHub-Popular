package matrixsystems.core.testutils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import matrixsystems.core.constants.Status
import matrixsystems.core.model.Resource
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by Shahbaz Hashmi on 2020-03-09.
 */

/* Copyright 2019 Google LLC.
   SPDX-License-Identifier: Apache-2.0 */
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}


fun <T> LiveData<Resource<List<T>>>.observeForApiTesting(
    timeout: Long,
    block: (Resource<List<T>>) -> Unit
) {
    val time: Long = timeout
    val timeUnit: TimeUnit = TimeUnit.SECONDS
    val latch = CountDownLatch(1)
    val observer = object : Observer<Resource<List<T>>> {
        override fun onChanged(o: Resource<List<T>>) {
            if (o.status != Status.LOADING) {
                // added try to avoid below TimeoutException
                // in case of AssertionException
                try {
                    block(o)
                } finally {
                    latch.countDown()
                    this@observeForApiTesting.removeObserver(this)
                }
            }
        }
    }
    try {
        observeForever(observer)
    } finally {
        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }
    }
}