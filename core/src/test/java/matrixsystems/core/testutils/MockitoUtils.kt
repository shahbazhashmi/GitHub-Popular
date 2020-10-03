package matrixsystems.core.testutils

import androidx.lifecycle.MutableLiveData
import matrixsystems.core.constants.Status
import matrixsystems.core.model.Resource

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