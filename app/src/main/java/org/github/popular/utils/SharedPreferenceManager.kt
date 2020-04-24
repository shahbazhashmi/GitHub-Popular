package org.github.popular.utils

import android.content.SharedPreferences
import org.github.popular.BuildConfig
import javax.inject.Inject


/**
 * Created by Shahbaz Hashmi on 2020-03-08.
 */
class SharedPreferenceManager @Inject constructor(val sharedPreferences: SharedPreferences) {

    val KEY_CACHE_TIMOUT = "cache_timeout"

    lateinit var editor: SharedPreferences.Editor

    fun setLastUpdatedTimestamp(timestamp: Long = System.currentTimeMillis()) {
        editor = sharedPreferences.edit()
        editor.putLong(KEY_CACHE_TIMOUT, timestamp)
        editor.commit()
    }

    /**
     * logic to check if cache data expired
     */
    fun isLocalDataExpired(): Boolean {
        if (System.currentTimeMillis() - sharedPreferences.getLong(
                KEY_CACHE_TIMOUT,
                0
            ) > BuildConfig.CACHE_TIMEOUT
        ) {
            return true
        }
        return false
    }

}