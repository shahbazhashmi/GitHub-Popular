package matrixsystems.feature.githubrepo.repolist

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import matrixsystems.datasource.api.network.Resource
import matrixsystems.datasource.model.GithubRepo
import matrixsystems.datasource.repo.GithubRepoRepository
import matrixsystems.core.ui.loader.LoaderHelper
import javax.inject.Inject

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */
internal class GithubRepoViewModel @Inject constructor(
    private val githubRepoRepository: GithubRepoRepository,
    val loaderHelper: LoaderHelper, val githubRepoAdapter: GithubRepoAdapter
) : ViewModel() {

    // FOR DATA
    private val _repos = MediatorLiveData<Resource<List<GithubRepo>?>>()
    private var reposSource: LiveData<Resource<List<GithubRepo>?>> = MutableLiveData()
    val repos: LiveData<Resource<List<GithubRepo>?>> get() = _repos


    fun fetchGithubRepos(callApiForcefully: Boolean) = viewModelScope.launch {
        _repos.removeSource(reposSource)
            reposSource = githubRepoRepository.getGithubRepos(callApiForcefully)
            _repos.addSource(reposSource) {
                _repos.value = it
            }
    }

}