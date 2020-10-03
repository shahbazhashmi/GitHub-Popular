package matrixsystems.feature.githubrepo

import kotlinx.coroutines.runBlocking
import matrixsystems.datasource.api.ApiServiceHelper
import matrixsystems.datasource.utils.DataSourceUtil
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Shahbaz Hashmi on 02/10/20.
 */
class ApiServiceHelperTest {

    lateinit var apiServiceHelper: ApiServiceHelper

    @Before
    fun setup() {
        apiServiceHelper = ApiServiceHelper(DataSourceUtil.getApiService())
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