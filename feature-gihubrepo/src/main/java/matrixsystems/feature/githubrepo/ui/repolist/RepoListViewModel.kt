package matrixsystems.feature.githubrepo.ui.repolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import matrixsystems.core.ui.BaseViewModel
import matrixsystems.core.ui.loader.LoaderHelper
import matrixsystems.datasource.model.Resource
import matrixsystems.feature.githubrepo.data.models.GithubRepo
import matrixsystems.feature.githubrepo.repo.GithubRepoRepository
import javax.inject.Inject

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */
class RepoListViewModel @Inject constructor(
    private val githubRepoRepository: GithubRepoRepository,
    val loaderHelper: LoaderHelper, val repoListAdapter: RepoListAdapter
) : BaseViewModel() {

    // FOR DATA
    private val _repos = MediatorLiveData<Resource<List<GithubRepo>?>>()
    private var reposSource: LiveData<Resource<List<GithubRepo>?>> = MutableLiveData()
    val repos: LiveData<Resource<List<GithubRepo>?>> get() = _repos


    fun fetchGithubRepos(callApiForcefully: Boolean) = launch {
        _repos.removeSource(reposSource)
        reposSource = githubRepoRepository.getGithubRepos(callApiForcefully)
        _repos.addSource(reposSource) {
            _repos.value = it
        }
    }

}