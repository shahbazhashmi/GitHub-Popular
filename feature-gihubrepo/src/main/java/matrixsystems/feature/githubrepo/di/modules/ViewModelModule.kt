package matrixsystems.feature.githubrepo.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import matrixsystems.core.di.base.ViewModelKey
import matrixsystems.feature.githubrepo.ui.landing.GithubRepoLandingViewModel
import matrixsystems.feature.githubrepo.ui.repolist.RepoListViewModel

/**
 * Created by Shahbaz Hashmi on 03/10/20.
 */
@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RepoListViewModel::class)
    abstract fun bindGithubRepoViewModel(gitRepoListViewModel: RepoListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GithubRepoLandingViewModel::class)
    abstract fun bindGithubRepoLandingViewModel(githubRepoLandingViewModel: GithubRepoLandingViewModel): ViewModel
}