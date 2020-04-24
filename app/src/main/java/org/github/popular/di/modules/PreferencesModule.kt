package org.github.popular.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import org.github.popular.utils.SharedPreferenceManager
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
    fun provideSharedPreference(context: Context) =
        context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE)


    /**
     * Provides SharedPreferenceManager object
     */
    @Provides
    @Singleton
    fun provideSharedPreferenceManager(sharedPreferences: SharedPreferences) =
        SharedPreferenceManager(sharedPreferences)

}