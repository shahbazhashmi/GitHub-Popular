package org.github.popular.repository.api.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import org.github.popular.repository.api.network.livedata.LiveDataCallAdapter;
import org.github.popular.repository.api.network.resource.ResourceCallAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by Shahbaz Hashmi on 30/09/20.
 */
public class RetrofitCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        /**
         * check if call is of type LiveData
         */
        if (getRawType(returnType) == LiveData.class) {
            Class<?> rawObservableType = getRawType(observableType);
            if (rawObservableType != Resource.class) {
                throw new IllegalArgumentException("type must be a resource");
            } else {
                return new LiveDataCallAdapter<>(bodyType);
            }
        }
        /**
         * check if call is of type Resource
         */
        else if(getRawType(returnType) == Resource.class) {
            return new ResourceCallAdapter(bodyType);
        }
        else {
            return null;
        }
    }
}

