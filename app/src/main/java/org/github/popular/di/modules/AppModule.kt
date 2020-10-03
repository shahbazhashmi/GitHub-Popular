package org.github.popular.di.modules

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import matrixsystems.feature.githubrepo.di.modules.GithubRepoModule
import org.github.popular.app.AppController
import matrixsystems.feature.githubrepo.repolist.GithubRepoAdapter
import javax.inject.Singleton

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */

@Module(includes = [ActivityModule::class, ViewModelModule::class])
class AppModule {

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