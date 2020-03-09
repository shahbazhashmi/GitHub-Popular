package org.gojek.github.repository.repo

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.gojek.github.BuildConfig.BASE_URL
import org.gojek.github.app.AppExecutors
import org.gojek.github.repository.api.ApiService
import org.gojek.github.repository.api.network.LiveDataCallAdapterFactoryForRetrofit
import org.gojek.github.repository.api.network.Status
import org.gojek.github.repository.db.githubrepo.GithubRepoDao
import org.gojek.github.repository.observeForApiTesting
import org.gojek.github.utils.SharedPreferenceManager
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
        apiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactoryForRetrofit())
            .build()
            .create(ApiService::class.java)
        githubRepoRepository = GithubRepoRepository(githubRepoDao, apiService, context, sharedPreferenceManager, appExecutors)
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