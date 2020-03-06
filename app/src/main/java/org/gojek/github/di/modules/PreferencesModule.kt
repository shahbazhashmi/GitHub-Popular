package org.gojek.github.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */
@Module
class PreferencesModule {

    /**
     * Provides Preferences object with MODE_PRIVATE
     */
    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences =
        app.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE)

    /**
     * OR above code can also written like this it is actually returning SharedPreferences object
     *
     * You can inject it like this
     *  @Inject
     *  lateinit var sharedPreferences: SharedPreferences
     */

    /**
    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences {
    return app.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE)
    }*/

}