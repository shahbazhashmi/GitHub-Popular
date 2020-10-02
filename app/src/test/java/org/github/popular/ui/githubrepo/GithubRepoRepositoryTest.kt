package org.github.popular.ui.githubrepo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.github.popular.repository.api.network.Resource
import org.github.popular.repository.api.network.Status
import org.github.popular.repository.model.GithubRepo
import org.github.popular.repository.repo.GithubRepoRepository
import org.github.popular.ui.githubrepo.mockitoutils.TestCoroutineRule
import org.github.popular.ui.loader.LoaderHelper
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
class GithubRepoRepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiRepoObserver: Observer<Resource<List<GithubRepo>?>>

    @Mock
    lateinit var mockRepository: GithubRepoRepository

    @Mock
    lateinit var viewModel: GithubRepoViewModel

    @Before
    fun setUp() {
        /*viewModel = GithubRepoViewModel(
            mockRepository, Mockito.mock(LoaderHelper::class.java), Mockito.mock(
                GithubRepoAdapter::class.java
            )
        )*/
        //viewModel.repos.observeForever(apiRepoObserver)
    }

    @Test
    fun fetchRepoTest() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(emptyList<Resource<List<GithubRepo>?>>())
                .`when`(mockRepository)
                .getGithubRepos(true).value
            //viewModel.loadGithubRepos(true)
            viewModel.repos.observeForever(apiRepoObserver)
            Mockito.verify(mockRepository).getGithubRepos(true)
            Mockito.verify(apiRepoObserver).onChanged(Resource.success(emptyList()))
            viewModel.repos.removeObserver(apiRepoObserver)
            /*val repoData = mockRepository.getGithubRepos(true)
            Assert.assertEquals(repoData, "datascsc")*/
        }
    }

    @Test
    fun test() {
        // using Mockito.mock() method
        val mockList = Mockito.mock(MutableList::class.java)
        Mockito.`when`(mockList.size).thenReturn(5)
        assertTrue(mockList.size == 5)
    }

    @Test
    fun fetchGithubReposStateTest() {
            Mockito.doReturn(getRepoStatusFactory<List<GithubRepo>?>(Status.LOADING))
                .`when`(viewModel)
                .repos

            viewModel.repos.observeForever(apiRepoObserver)
            viewModel.fetchGithubRepos(true)
            Mockito.verify(apiRepoObserver).onChanged(Resource.loading())
            //assert(viewModel.repos.value?.status == Status.LOADING)
    }


    private fun <T> getRepoStatusFactory(status: Status) : MutableLiveData<Resource<T>> {
        val mutableLiveData = MutableLiveData<Resource<T>>()
        mutableLiveData.value = Resource(status)
        return mutableLiveData
    }

}