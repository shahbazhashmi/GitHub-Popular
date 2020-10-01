package org.github.popular.ui.githubrepo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.github.popular.repository.api.network.Resource
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
import org.mockito.Mockito.*
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

    lateinit var viewModel: GithubRepoViewModel

    @Before
    fun setUp() {
        viewModel = GithubRepoViewModel(
            mockRepository, Mockito.mock(LoaderHelper::class.java), Mockito.mock(
                GithubRepoAdapter::class.java
            )
        )
        viewModel.repoLiveData?.observeForever(apiRepoObserver)
    }

    @Test
    fun fetchRepoTest() {
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<Resource<List<GithubRepo>?>>())
                .`when`(mockRepository)
                .getGithubRepos(true).value
            viewModel.loadGithubRepos(true)
            viewModel.repoLiveData?.observeForever(apiRepoObserver)
            verify(mockRepository).getGithubRepos(true)
            verify(apiRepoObserver).onChanged(Resource.success(emptyList()))
            viewModel.repoLiveData?.removeObserver(apiRepoObserver)
            /*val repoData = mockRepository.getGithubRepos(true)
            Assert.assertEquals(repoData, "datascsc")*/
        }
    }

    @Test
    fun test() {
        // using Mockito.mock() method
        val mockList = mock(MutableList::class.java)
        `when`(mockList.size).thenReturn(5)
        assertTrue(mockList.size == 5)
    }

}