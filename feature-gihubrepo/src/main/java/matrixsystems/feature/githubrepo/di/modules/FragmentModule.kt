package matrixsystems.feature.githubrepo.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import matrixsystems.feature.githubrepo.ui.repolist.RepoListFragment

/**
 * Created by Shahbaz Hashmi on 23/04/21.
 */
@Module
abstract class FragmentModule {
    /**
     * Injecting Fragments
     */
    @ContributesAndroidInjector
    internal abstract fun contributeRepoListFragment(): RepoListFragment

}