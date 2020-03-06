package org.gojek.github.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.gojek.github.GithubRepoActivity

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */

/**
 * All your Activities participating in DI would be listed here.
 */
@Module
abstract class ActivityModule {

    /**
     * Marking Activities to be available to contributes for Android Injector
     */
    @ContributesAndroidInjector
    abstract fun contributeGitHubRepoActivity(): GithubRepoActivity
}