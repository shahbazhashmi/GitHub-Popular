package matrixsystems.datasource.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import matrixsystems.core.di.modules.CoreModule
import matrixsystems.datasource.api.ApiService
import matrixsystems.datasource.api.ApiServiceHelper
import matrixsystems.datasource.db.AppDatabase
import matrixsystems.datasource.db.DatabaseDaoHelper
import matrixsystems.datasource.db.githubrepo.DatabaseDao
import matrixsystems.datasource.utils.DataSourceUtil
import javax.inject.Singleton

/**
 * Created by Shahbaz Hashmi on 03/10/20.
 */
@Module
class DataSourceModule {
    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    fun provideNewsService(): ApiService = DataSourceUtil.getApiService()

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
    fun provideUserDao(db: AppDatabase): DatabaseDao {
        return db.githubRepoDao()
    }

    @Provides
    @Singleton
    fun providesGithubRepoDbHelper(databaseDao: DatabaseDao): DatabaseDaoHelper =
        DatabaseDaoHelper(databaseDao)


    @Provides
    @Singleton
    fun providesApiServiceHelper(apiService: ApiService) = ApiServiceHelper(apiService)

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
        matrixsystems.datasource.SharedPreferenceManager(sharedPreferences)
}