package matrixsystems.feature.githubrepo.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import matrixsystems.feature.githubrepo.ui.repolist.RepoListAdapter

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


}