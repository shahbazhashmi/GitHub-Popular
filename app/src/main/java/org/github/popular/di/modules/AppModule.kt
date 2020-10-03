package org.github.popular.di.modules

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import matrixsystems.datasource.di.modules.DataSourceModule
import org.github.popular.app.AppController
import org.github.popular.ui.githubrepo.GithubRepoAdapter
import org.github.popular.ui.loader.LoaderHelper
import javax.inject.Singleton

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */

@Module(includes = [DataSourceModule::class, ActivityModule::class, ViewModelModule::class])
class AppModule {

    /**
     * Provides LoaderHelper an object
     */
    @Provides
    fun provideLoaderHelper(): LoaderHelper {
        return LoaderHelper()
    }


    /**
     * Provides GithubRepoAdapter an object
     */
    @Provides
    fun provideGithubRepoAdapter(context: Context): GithubRepoAdapter {
        return GithubRepoAdapter(context)
    }

    /**
     * Application application level context.
     */
    @Singleton
    @Provides
    fun provideContext(application: AppController): Context {
        return application.applicationContext
    }


    /**
     * Application resource provider, so that we can get the Drawable, Color, String etc at runtime
     */
    @Provides
    @Singleton
    fun providesResources(application: AppController): Resources = application.resources

}