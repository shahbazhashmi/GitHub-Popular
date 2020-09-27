package org.github.popular.repository.repo

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.github.popular.BuildConfig.BASE_URL
import org.github.popular.repository.api.ApiService
import org.github.popular.repository.api.network.LiveDataCallAdapterFactoryForRetrofit
import org.github.popular.repository.api.network.Status
import org.github.popular.repository.db.githubrepo.GithubRepoDao
import org.github.popular.repository.observeForApiTesting
import org.github.popular.utils.SharedPreferenceManager
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Shahbaz Hashmi on 2020-03-09.
 */
class GithubRepoRepositoryTest {

    lateinit var githubRepoRepository: GithubRepoRepository

    @Mock
    lateinit var githubRepoDao: GithubRepoDao

    lateinit var apiService: _root_ide_package_.org.github.popular.repository.api.ApiService

    @Mock
    lateinit var context: Context

    @InjectMocks
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    @Mock
    lateinit var sharedPreferences: SharedPreferences


    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        apiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(_root_ide_package_.org.github.popular.repository.api.network.LiveDataCallAdapterFactoryForRetrofit())
            .build()
            .create(_root_ide_package_.org.github.popular.repository.api.ApiService::class.java)
        githubRepoRepository = GithubRepoRepository(githubRepoDao, apiService, context, sharedPreferenceManager)
    }


    @Test
    fun testGetGithubRepos() {
        val data = githubRepoRepository.getGithubReposFromServerOnly()
        data.observeForApiTesting(10) {
            if(it.status == Status.ERROR) {
                System.out.println("API ERROR -> ${it.errorMessage}")
                assert(false)
            }
            else {
                System.out.println("API RESPONSE -> ${it.data}")
                assert(true)
            }
        }
    }
}