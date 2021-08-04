package matrixsystems.feature.githubrepo

import kotlinx.coroutines.runBlocking
import matrixsystems.feature.githubrepo.data.api.GithubRepoApiServiceHelper
import matrixsystems.datasource.utils.DataSourceUtil
import matrixsystems.feature.githubrepo.data.api.GithubRepoApiService
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Shahbaz Hashmi on 02/10/20.
 */
class ApiServiceHelperTest {

    lateinit var apiServiceHelper: GithubRepoApiServiceHelper

    @Before
    fun setup() {
        apiServiceHelper = GithubRepoApiServiceHelper(DataSourceUtil.getApiService(
            GithubRepoApiService::class.java,
            BuildConfig.BASE_URL
        ) as GithubRepoApiService)
    }

    @Test
    fun getReposTest() = runBlocking {
        val apiResponse = apiServiceHelper.getRepos()
        if (apiResponse.status.isSuccessful()) {
            Assert.assertTrue("data fetched successfully", true)
        } else {
            throw Exception(apiResponse.errorMessage)
        }
    }
}