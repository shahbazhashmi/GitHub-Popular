package matrixsystems.core.di.modules

import dagger.Module
import dagger.Provides
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

}