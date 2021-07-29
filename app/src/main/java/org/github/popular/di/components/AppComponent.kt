package org.github.popular.di.components

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import matrixsystems.core.di.modules.CoreModule
import matrixsystems.datasource.di.modules.DataSourceModule
import matrixsystems.feature.githubrepo.di.modules.GithubRepoModule
import org.github.popular.app.AppController
import org.github.popular.di.modules.AppModule
import javax.inject.Singleton

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */

/**
 * AndroidInjectionModule::class to support Dagger
 * AppModule::class is loading all modules for app
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, CoreModule::class, DataSourceModule::class, GithubRepoModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: AppController): Builder

        fun build(): AppComponent
    }

    fun inject(app: AppController)

}