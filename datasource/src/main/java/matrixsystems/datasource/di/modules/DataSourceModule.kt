package matrixsystems.datasource.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import matrixsystems.datasource.BuildConfig
import javax.inject.Singleton

/**
 * Created by Shahbaz Hashmi on 03/10/20.
 */
@Module
class DataSourceModule() {

    /**
     * Provides Preferences object with MODE_PRIVATE
     */
    @Provides
    @Singleton
    fun provideSharedPreference(context: Context) =
        context.getSharedPreferences(BuildConfig.SP_NAME, Context.MODE_PRIVATE)

}