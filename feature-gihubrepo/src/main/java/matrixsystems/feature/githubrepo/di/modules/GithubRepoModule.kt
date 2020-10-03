package matrixsystems.feature.githubrepo.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import matrixsystems.datasource.di.modules.DataSourceModule
import matrixsystems.feature.githubrepo.ui.repolist.RepoListAdapter

/**
 * Created by Shahbaz Hashmi on 03/10/20.
 */
@Module(includes = [DataSourceModule::class, ActivityModule::class, ViewModelModule::class])
class GithubRepoModule {

    /**
     * Provides GithubRepoAdapter an object
     */
    @Provides
    fun provideGithubRepoAdapter(context: Context): RepoListAdapter {
        return RepoListAdapter(context)
    }


}