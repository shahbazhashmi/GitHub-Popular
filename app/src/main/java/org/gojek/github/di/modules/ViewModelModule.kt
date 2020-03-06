package org.gojek.github.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.gojek.github.di.base.ViewModelFactory
import org.gojek.github.di.base.ViewModelKey
import org.gojek.github.ui.githubrepo.GithubRepoViewModel

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */
@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(GithubRepoViewModel::class)
    abstract fun bindGithubRepoViewModel(countriesViewModel: GithubRepoViewModel): ViewModel

    /**
     * Binds ViewModels factory to provide ViewModels.
     */
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}