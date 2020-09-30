package org.github.popular.repository.api.network.resource

import com.google.gson.internal.`$Gson$Types`.getRawType
import org.github.popular.repository.api.network.Resource
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
                    ResourceAdapter(resultType)
                }
                else -> null
            }
        }
        else -> null
    }
}