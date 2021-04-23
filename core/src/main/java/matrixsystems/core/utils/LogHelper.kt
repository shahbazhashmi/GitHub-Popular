package matrixsystems.core.utils

import android.util.Log

/**
 * Created by Shahbaz Hashmi on 23/04/21.
 */
object LogHelper {

    // todo - it should be based on build type
    val isLogEnabled = true;

    fun i(tag: String, message: String) {
        if (isLogEnabled)
            try {
                Log.i(tag, message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }

    fun v(tag: String, message: String) {
        if (isLogEnabled)
            try {
                Log.v(tag, message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }

    fun e(tag: String, exception: Exception) {
        if (isLogEnabled)
            try {
                Log.e(tag, exception.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }

    fun e(tag: String, message: String) {
        if (isLogEnabled)
            try {
                Log.e(tag, message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }

    fun d(tag: String, message: String) {
        if (isLogEnabled)
            try {
                Log.d(tag, message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }

}