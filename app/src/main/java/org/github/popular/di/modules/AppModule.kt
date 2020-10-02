package org.github.popular.di.modules

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import dagger.Module
import dagger.Provides
import org.github.popular.app.AppController
import org.github.popular.repository.api.ApiService
import org.github.popular.repository.api.ApiServiceHelper
import org.github.popular.repository.db.AppDatabase
import org.github.popular.repository.db.DatabaseDaoHelper
import org.github.popular.repository.db.githubrepo.databaseDao
import org.github.popular.ui.githubrepo.GithubRepoAdapter
import org.github.popular.ui.loader.LoaderHelper
import org.github.popular.utils.AppUtil
import javax.inject.Singleton

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */

@Module(includes = [PreferencesModule::class, ActivityModule::class, ViewModelModule::class])
class AppModule {

    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    fun provideNewsService(): ApiService = AppUtil.getApiService()


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
     * Provides app AppDatabase
     */
    @Singleton
    @Provides
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "github-db").build()
    }


    /**
     * Provides GithubRepoDao an object to access GithubRepo table from Database
     */
    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): databaseDao {
        return db.githubRepoDao()
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

    @Provides
    @Singleton
    fun providesGithubRepoDbHelper(databaseDao: databaseDao): DatabaseDaoHelper =
        DatabaseDaoHelper(databaseDao)


    @Provides
    @Singleton
    fun providesApiServiceHelper(apiService: ApiService) = ApiServiceHelper(apiService)

}