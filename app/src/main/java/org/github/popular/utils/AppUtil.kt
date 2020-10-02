package org.github.popular.utils

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.github.popular.BuildConfig
import org.github.popular.repository.api.ApiService
import org.github.popular.repository.api.network.resource.ResourceCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Shahbaz Hashmi on 2020-03-08.
 */
object AppUtil {

    fun showToast(
        context: Context?,
        message: String?,
        longDuration: Boolean
    ) {
        try {
            if (context is Activity && (context as Activity?)!!.isFinishing) {
                return
            }
            if (TextUtils.isEmpty(message) || context == null) return
            val handler = Handler(Looper.getMainLooper())
            handler.post(
                Toast.makeText(
                    context.applicationContext,
                    message,
                    if (longDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
                )::show
            )
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getApiService() : ApiService {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging) // <-- this is the important line!

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResourceCallAdapterFactory())
            .client(httpClient.build())
            .build()
            .create(ApiService::class.java)
    }

}