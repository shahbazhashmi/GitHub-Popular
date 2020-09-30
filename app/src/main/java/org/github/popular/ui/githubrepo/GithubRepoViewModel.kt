package org.github.popular.ui.githubrepo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import org.github.popular.repository.repo.GithubRepoRepository
import org.github.popular.ui.loader.LoaderHelper
import javax.inject.Inject

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */
class GithubRepoViewModel @Inject constructor(
    val githubRepoRepository: GithubRepoRepository,
    val loaderHelper: LoaderHelper, val githubRepoAdapter: GithubRepoAdapter
) : ViewModel() {

    /*fun loadGithubRepos(callApiForcefully: Boolean) = liveData {
        emit(githubRepoRepository.getGithubRepos(callApiForcefully).value)
    }*/

}