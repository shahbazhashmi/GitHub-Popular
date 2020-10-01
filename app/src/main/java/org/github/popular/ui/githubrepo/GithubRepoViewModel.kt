package org.github.popular.ui.githubrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.github.popular.repository.api.network.Resource
import org.github.popular.repository.model.GithubRepo
import org.github.popular.repository.repo.GithubRepoRepository
import org.github.popular.ui.loader.LoaderHelper
import javax.inject.Inject

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */
class GithubRepoViewModel @Inject constructor(
    private val githubRepoRepository: GithubRepoRepository,
    val loaderHelper: LoaderHelper, val githubRepoAdapter: GithubRepoAdapter
) : ViewModel() {

    var repoLiveData : LiveData<Resource<List<GithubRepo>?>>? = null

    fun loadGithubRepos(callApiForcefully: Boolean) = viewModelScope.launch {
        repoLiveData = githubRepoRepository.getGithubRepos(callApiForcefully)
    }

}