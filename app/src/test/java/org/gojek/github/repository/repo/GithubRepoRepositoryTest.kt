package org.gojek.github.repository.repo

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertEquals
import org.gojek.github.app.AppExecutors
import org.gojek.github.repository.api.ApiService
import org.gojek.github.repository.api.network.Resource
import org.gojek.github.repository.api.network.Status
import org.gojek.github.repository.db.githubrepo.GithubRepoDao
import org.gojek.github.repository.getOrAwaitValue
import org.gojek.github.repository.model.GithubRepo
import org.gojek.github.repository.observeForApiTesting
import org.gojek.github.utils.SharedPreferenceManager
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations


/**
 * Created by Shahbaz Hashmi on 2020-03-09.
 */
class GithubRepoRepositoryTest {

    lateinit var githubRepoRepository: GithubRepoRepository

    @Mock
    lateinit var githubRepoDao: GithubRepoDao

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var context: Context

    @InjectMocks
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var appExecutors: AppExecutors

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        githubRepoRepository = GithubRepoRepository(githubRepoDao, apiService, context, sharedPreferenceManager, appExecutors)
    }


    @Test
    fun testGetGithubRepos() {
        val data = githubRepoRepository.getGithubRepos(true)
        data.observeForApiTesting<GithubRepo> {
            if(it.status == Status.ERROR) {
                System.out.println("API ERROR -> ${it.errorMessage}")
                assert(false)
            }
            else {
                System.out.println("API RESPONSE -> ${it?.data}")
                assert(true)
            }
        }
    }
}