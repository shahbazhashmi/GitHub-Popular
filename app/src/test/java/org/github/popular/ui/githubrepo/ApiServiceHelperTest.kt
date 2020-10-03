package org.github.popular.ui.githubrepo

import kotlinx.coroutines.runBlocking
import org.github.popular.utils.AppUtil
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
        apiServiceHelper = ApiServiceHelper(AppUtil.getApiService())
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