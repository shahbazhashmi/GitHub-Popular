package org.github.popular.utils

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast



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

}