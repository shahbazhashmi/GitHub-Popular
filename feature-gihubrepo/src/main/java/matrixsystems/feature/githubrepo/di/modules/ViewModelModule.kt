package matrixsystems.feature.githubrepo.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import matrixsystems.core.di.base.ViewModelKey
import matrixsystems.feature.githubrepo.repolist.GithubRepoViewModel

/**
 * Created by Shahbaz Hashmi on 03/10/20.
 */
@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GithubRepoViewModel::class)
    abstract fun bindGithubRepoViewModel(gitRepoViewModel: GithubRepoViewModel): ViewModel
}