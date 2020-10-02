package org.github.popular.ui.githubrepo.utils

import androidx.lifecycle.MutableLiveData
import org.github.popular.repository.api.network.Resource
import org.github.popular.repository.api.network.Status

/**
 * Created by Shahbaz Hashmi on 02/10/20.
 */
object MockitoUtils {

    fun <T> getMutableLiveDataOfResource(status: Status) : MutableLiveData<Resource<T>> {
        val mutableLiveData = MutableLiveData<Resource<T>>()
        mutableLiveData.value = Resource(status)
        return mutableLiveData
    }

}