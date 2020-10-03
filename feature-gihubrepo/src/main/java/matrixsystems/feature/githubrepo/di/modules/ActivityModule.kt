package matrixsystems.feature.githubrepo.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import matrixsystems.feature.githubrepo.repolist.GithubRepoActivity

/**
 * Created by Shahbaz Hashmi on 03/10/20.
 */
@Module
internal abstract class ActivityModule {
    /**
     * Marking Activities to be available to contributes for Android Injector
     */
    @ContributesAndroidInjector
    abstract fun contributeGitHubRepoActivity(): GithubRepoActivity
}