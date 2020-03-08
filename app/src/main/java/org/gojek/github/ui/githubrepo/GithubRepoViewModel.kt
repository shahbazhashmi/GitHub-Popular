package org.gojek.github.ui.githubrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.gojek.github.repository.api.network.Resource
import org.gojek.github.repository.model.GithubRepo
import org.gojek.github.repository.repo.GithubRepoRepository
import org.gojek.github.ui.loader.LoaderHelper
import javax.inject.Inject

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */
class GithubRepoViewModel @Inject constructor(
    private val githubRepoRepository: GithubRepoRepository,
    val loaderHelper: LoaderHelper, val githubRepoAdapter: GithubRepoAdapter
) : ViewModel() {


    private fun githubRepos(): LiveData<Resource<List<GithubRepo>?>> =
        githubRepoRepository.getGithubRepos(false)

    fun getGithubRepos() = githubRepos()


    private fun githubReposFromOnlyServer() =
        githubRepoRepository.getGithubRepos(true)

    fun getGithubReposFromServer() = githubReposFromOnlyServer()

}