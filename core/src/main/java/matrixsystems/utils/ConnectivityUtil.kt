package matrixsystems.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Shahbaz Hashmi on 2020-03-07.
 */
object ConnectivityUtil {

    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}