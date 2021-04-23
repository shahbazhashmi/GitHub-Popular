package matrixsystems.core.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import matrixsystems.core.SharedPreferenceManager
import matrixsystems.core.ui.loader.LoaderHelper
import matrixsystems.core.utils.CoreUtil
import javax.inject.Singleton

/**
 * Created by Shahbaz Hashmi on 03/10/20.
 */
@Module
class CoreModule {

    /**
     * Provides LoaderHelper an object
     */
    @Provides
    fun provideLoaderHelper(): LoaderHelper {
        return LoaderHelper()
    }

    @Provides
    @Singleton
    fun provideCoreUtil() = CoreUtil()


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