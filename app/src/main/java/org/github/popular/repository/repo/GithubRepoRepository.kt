package org.github.popular.repository.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

        /*return object :
            NetworkAndDBBoundResource<List<GithubRepo>, List<GithubRepo>>() {
            override fun saveCallResult(item: List<GithubRepo>) {
                if (!item.isEmpty()) {
                    sharedPreferenceManager.setLastUpdatedTimestamp()
                    CoroutineScope(Dispatchers.Main).launch {
                        githubRepoDbHelper.deleteAllRepos()
                        githubRepoDbHelper.insertRepos(item)
                    }
                }
            }

            override fun mustFetch(data: List<GithubRepo>?) = callApiForcefully

            override fun shouldFetch(data: List<GithubRepo>?): Boolean {
                if (ConnectivityUtil.isConnected(context) && sharedPreferenceManager.isLocalDataExpired()) {
                    return true
                }
                return false
            }

            override fun loadFromDb() = liveData(Dispatchers.Main) {
                emit(githubRepoDbHelper.getAllRepos())
            }

            override fun createCall() = apiServiceHelper.getRepos()


        }.asLiveData()*/

        return object :
            NetworkAndDBBoundResource<List<GithubRepo>?, List<GithubRepo>>() {
            override suspend fun saveCallResults(items: List<GithubRepo>?) {
                if (items != null && !items.isEmpty()) {
                    sharedPreferenceManager.setLastUpdatedTimestamp()
                        githubRepoDbHelper.deleteAllRepos()
                        githubRepoDbHelper.insertRepos(items)

                }
            }

            override fun shouldFetch(data: List<GithubRepo>?): Boolean {
                if (ConnectivityUtil.isConnected(context) && sharedPreferenceManager.isLocalDataExpired()) {
                    return true
                }
                if(data == null || data.isEmpty()) {
                    return true
                }
                return false
            }

            override suspend fun loadFromDb(): List<GithubRepo> = githubRepoDbHelper.getAllRepos()

            override suspend fun createCall(): Resource<List<GithubRepo>> = apiServiceHelper.getRepos()
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