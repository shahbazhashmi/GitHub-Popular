package matrixsystems.datasource.utils

import matrixsystems.datasource.BuildConfig
import matrixsystems.datasource.api.ApiService
import matrixsystems.datasource.api.network.resource.ResourceCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Shahbaz Hashmi on 03/10/20.
 */
object DataSourceUtil {
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