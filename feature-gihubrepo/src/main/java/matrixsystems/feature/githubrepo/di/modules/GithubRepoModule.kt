package matrixsystems.feature.githubrepo.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import matrixsystems.datasource.utils.DataSourceUtil
import matrixsystems.feature.githubrepo.BuildConfig
import matrixsystems.feature.githubrepo.data.api.GithubRepoApiService
import matrixsystems.feature.githubrepo.data.api.GithubRepoApiServiceHelper
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


}