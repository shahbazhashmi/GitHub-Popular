package org.github.popular.repository.repo

import android.content.Context
import androidx.lifecycle.LiveData
import org.github.popular.repository.api.ApiServiceHelper
import org.github.popular.repository.api.network.NetworkAndDBBoundResource
import org.github.popular.repository.api.network.Resource
import org.github.popular.repository.db.GithubRepoDbHelper
import org.github.popular.repository.model.GithubRepo
import org.github.popular.utils.ConnectivityUtil
import org.github.popular.utils.SharedPreferenceManager
import javax.inject.Inject

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
class GithubRepoRepository @Inject constructor(
    private val githubRepoDbHelper: GithubRepoDbHelper,
    private val apiServiceHelper: ApiServiceHelper,
    private val context: Context,
    private val sharedPreferenceManager: SharedPreferenceManager
) {
    private val TAG = "GithubRepoRepository"
    /**
     * Fetch the repos from database if exist else fetch from web
     * and persist them in the database
     */
    suspend fun getGithubRepos(callApiForcefully: Boolean): LiveData<Resource<List<GithubRepo>?>> {

        return object :
            NetworkAndDBBoundResource<List<GithubRepo>?, List<GithubRepo>>() {
            override suspend fun saveCallResults(items: List<GithubRepo>?) {
                if (items != null && items.isNotEmpty()) {
                    sharedPreferenceManager.setLastUpdatedTimestamp()
                    githubRepoDbHelper.deleteAllRepos()
                    githubRepoDbHelper.insertRepos(items)

                }
            }

            override fun shouldFetch(data: List<GithubRepo>?): Boolean {
                if (ConnectivityUtil.isConnected(context) && sharedPreferenceManager.isLocalDataExpired()) {
                    return true
                }
                if (!isLocalDataAvailable(data)) {
                    return true
                }
                return false
            }

            override fun mustFetch(): Boolean = callApiForcefully

            override fun isLocalDataAvailable(data: List<GithubRepo>?): Boolean =
                data != null && data.isNotEmpty()

            override suspend fun loadFromDb(): List<GithubRepo> = githubRepoDbHelper.getAllRepos()

            override suspend fun createCall(): Resource<List<GithubRepo>> =
                apiServiceHelper.getRepos()
        }.build().asLiveData()
    }

    /**
     * Fetch the repos from database if exist else fetch from web
     * and persist them in the database
     * LiveData<Resource<githubRepoSource>>
     */
    /* fun getGithubReposFromServerOnly():
             LiveData<Resource<List<GithubRepo>>> {

         return object : NetworkResource<List<GithubRepo>>() {
             override fun createCall(): LiveData<Resource<List<GithubRepo>>> {
                 return apiServiceHelper.getRepos()
             }

         }.asLiveData()
     }*/

}