package org.github.popular.repository.api.network.resource

import org.github.popular.repository.api.network.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * Created by Shahbaz Hashmi on 30/09/20.
 */
class ResourceCallAdapter(
    private val type: Type
): CallAdapter<Type, Call<Resource<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<Resource<Type>> = ResourceResourceCall(call)
}