package matrixsystems.datasource.api.network.resource

import matrixsystems.datasource.api.network.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by Shahbaz Hashmi on 30/09/20.
 */
class ResourceCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                Resource::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    ResourceCallAdapter(resultType)
                }
                else -> null
            }
        }
        else -> null
    }
}
