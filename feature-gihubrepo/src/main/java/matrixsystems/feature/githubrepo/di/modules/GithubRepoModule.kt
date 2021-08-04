package matrixsystems.feature.githubrepo.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import matrixsystems.datasource.BuildConfig.DB_NAME
import matrixsystems.datasource.utils.DataSourceUtil
import matrixsystems.feature.githubrepo.BuildConfig
import matrixsystems.feature.githubrepo.data.GithubRepoSharedPreferenceManager
import matrixsystems.feature.githubrepo.data.api.GithubRepoApiService
import matrixsystems.feature.githubrepo.data.api.GithubRepoApiServiceHelper
import matrixsystems.feature.githubrepo.data.db.GithubRepoDatabase
import matrixsystems.feature.githubrepo.data.db.GithubRepoDatabaseDao
import matrixsystems.feature.githubrepo.data.db.GithubRepoDatabaseDaoHelper
import matrixsystems.feature.githubrepo.ui.repolist.RepoListAdapter
import javax.inject.Singleton

/**
 * Created by Shahbaz Hashmi on 03/10/20.
 */
@Module(includes = [ActivityModule::class, ViewModelModule::class, FragmentModule::class])
class GithubRepoModule {

    /**
     * Provides GithubRepoAdapter an object
     */
    @Provides
    fun provideGithubRepoAdapter(context: Context): RepoListAdapter {
        return RepoListAdapter(context)
    }

    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    fun provideGithubRepoApiService() = DataSourceUtil.getApiService(
        GithubRepoApiService::class.java,
        BuildConfig.BASE_URL
    ) as GithubRepoApiService

    @Provides
    @Singleton
    fun provideGithubRepoApiServiceHelper(apiService: GithubRepoApiService) =
        GithubRepoApiServiceHelper(apiService)


    /**
     * Provides app AppDatabase
     */
    @Singleton
    @Provides
    fun provideDb(context: Context): GithubRepoDatabase {
        return Room.databaseBuilder(context, GithubRepoDatabase::class.java, DB_NAME).build()
    }


    /**
     * Provides GithubRepoDao an object to access GithubRepo table from Database
     */
    @Singleton
    @Provides
    fun provideUserDao(db: GithubRepoDatabase): GithubRepoDatabaseDao {
        return db.githubRepoDao()
    }

    @Provides
    @Singleton
    fun providesGithubRepoDatabaseDaoHelper(databaseDao: GithubRepoDatabaseDao): GithubRepoDatabaseDaoHelper =
        GithubRepoDatabaseDaoHelper(databaseDao)


    /**
     * Provides SharedPreferenceManager object
     */
    @Provides
    @Singleton
    fun provideSharedPreferenceManager(sharedPreferences: SharedPreferences) =
        GithubRepoSharedPreferenceManager(sharedPreferences)


}