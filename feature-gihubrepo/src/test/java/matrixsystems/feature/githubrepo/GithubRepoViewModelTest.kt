package matrixsystems.feature.githubrepo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import matrixsystems.core.constants.Status
import matrixsystems.core.model.Resource
import matrixsystems.feature.githubrepo.testutils.MockTestCoroutineRule
import matrixsystems.feature.githubrepo.testutils.MockitoUtils
import matrixsystems.datasource.model.GithubRepo
import matrixsystems.feature.githubrepo.ui.repolist.RepoListViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by Shahbaz Hashmi on 01/10/20.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GithubRepoViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MockTestCoroutineRule()

    @Mock
    private lateinit var apiRepoObserver: Observer<Resource<List<GithubRepo>?>>

    @Mock
    lateinit var viewModel: RepoListViewModel

    @Before
    fun setUp() {
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        Mockito.doReturn(MockitoUtils.getMutableLiveDataOfResource<List<GithubRepo>?>(Status.SUCCESS))
            .`when`(viewModel)
            .repos

        viewModel.repos.observeForever(apiRepoObserver)
        viewModel.fetchGithubRepos(true)
        Mockito.verify(apiRepoObserver).onChanged(Resource(Status.SUCCESS))
        viewModel.repos.removeObserver(apiRepoObserver)
        //assert(viewModel.repos.value?.status == Status.LOADING)
    }


    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        val errorMessage = "Error Message For You"
        Mockito.doThrow(RuntimeException(errorMessage))
            .`when`(viewModel)
            .repos
        viewModel.repos.observeForever(apiRepoObserver)
        viewModel.fetchGithubRepos(true)
        Mockito.verify(apiRepoObserver).onChanged(
            Resource.error(
                RuntimeException(errorMessage).toString()
            )
        )
        viewModel.repos.removeObserver(apiRepoObserver)
    }

}