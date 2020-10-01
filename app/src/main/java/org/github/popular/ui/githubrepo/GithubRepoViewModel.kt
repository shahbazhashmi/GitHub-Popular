package org.github.popular.ui.githubrepo

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    // FOR DATA
    private val _repos = MediatorLiveData<Resource<List<GithubRepo>?>>()
    private var reposSource: LiveData<Resource<List<GithubRepo>?>> = MutableLiveData()
    val repos: LiveData<Resource<List<GithubRepo>?>> get() = _repos


    fun loadGithubRepos(callApiForcefully: Boolean) = viewModelScope.launch {
        _repos.removeSource(reposSource)
            reposSource = githubRepoRepository.getGithubRepos(callApiForcefully)
            _repos.addSource(reposSource) {
                _repos.value = it
            }
    }

}