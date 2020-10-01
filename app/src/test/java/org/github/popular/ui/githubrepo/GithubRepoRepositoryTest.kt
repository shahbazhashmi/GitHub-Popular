package org.github.popular.ui.githubrepo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.github.popular.repository.api.ApiServiceHelper
import org.github.popular.repository.api.network.Resource
import org.github.popular.repository.db.GithubRepoDbHelper
import org.github.popular.repository.model.GithubRepo
import org.github.popular.repository.repo.GithubRepoRepository
import org.github.popular.ui.githubrepo.mockitoutils.TestCoroutineRule
import org.github.popular.utils.SharedPreferenceManager
import org.junit.Assert
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
    private lateinit var apiRepoObserver: Observer<Resource<List<GithubRepo>>>

    lateinit var mockRepository: GithubRepoRepository

    @Before
    fun setUp() {
        //mockRepository = GithubRepoRepository(Mockito.mock(GithubRepoDbHelper::class.java), Mockito.mock(ApiServiceHelper::class.java), Mockito.mock(
        mockRepository = Mockito.mock(GithubRepoRepository::class.java)
    }

    @Test
    fun fetchRepos() {
        testCoroutineRule.runBlockingTest {
            val repoData = mockRepository.getGithubRepos(true)
            Assert.assertEquals(repoData, "datascsc")
        }
    }

}