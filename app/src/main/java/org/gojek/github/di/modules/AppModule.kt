package org.gojek.github.di.modules

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import dagger.Module
import dagger.Provides
import org.gojek.github.BuildConfig.BASE_URL
import org.gojek.github.app.AppController
import org.gojek.github.repository.api.ApiService
import org.gojek.github.repository.api.network.LiveDataCallAdapterFactoryForRetrofit
import org.gojek.github.repository.db.AppDatabase
import org.gojek.github.repository.db.githubrepo.GithubRepoDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideNewsService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactoryForRetrofit())
            .build()
            .create(ApiService::class.java)
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
    fun provideUserDao(db: AppDatabase): GithubRepoDao {
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
}