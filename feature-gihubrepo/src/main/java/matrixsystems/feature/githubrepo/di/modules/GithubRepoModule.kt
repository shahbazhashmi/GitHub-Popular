package matrixsystems.feature.githubrepo.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import matrixsystems.datasource.di.modules.DataSourceModule
import matrixsystems.feature.githubrepo.repolist.GithubRepoAdapter

/**
 * Created by Shahbaz Hashmi on 03/10/20.
 */
@Module(includes = [DataSourceModule::class, ActivityModule::class, ViewModelModule::class])
class GithubRepoModule {

    /**
     * Provides GithubRepoAdapter an object
     */
    @Provides
    fun provideGithubRepoAdapter(context: Context): GithubRepoAdapter {
        return GithubRepoAdapter(context)
    }


}