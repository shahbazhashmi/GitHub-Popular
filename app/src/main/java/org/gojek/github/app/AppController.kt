package org.gojek.github.app

import android.app.Application
import android.content.res.Resources

/**
 * Created by Shahbaz Hashmi on 2020-03-04.
 */
class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        resourses = resources
    }

    companion object {
        @get:Synchronized
        var instance: AppController? = null
            private set
        var resourses: Resources? = null
            private set

    }
}