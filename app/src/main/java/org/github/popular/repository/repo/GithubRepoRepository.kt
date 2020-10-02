package org.github.popular.repository.repo

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.github.popular.repository.api.ApiServiceHelper
import org.github.popular.repository.api.network.NetworkAndDBBoundResource
import org.github.popular.repository.api.network.Resource
import org.github.popular.repository.db.DatabaseDaoHelper
import org.github.popular.repository.model.GithubRepo
import org.github.popular.utils.ConnectivityUtil
import org.github.popular.utils.SharedPreferenceManager
import javax.inject.Inject

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
class GithubRepoRepository @Inject constructor(
    private val databaseDaoHelper: DatabaseDaoHelper,
    private val apiServiceHelper: ApiServiceHelper,
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val context: Context?
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
                withContext(Dispatchers.IO) {
                    if (items != null && items.isNotEmpty()) {
                        sharedPreferenceManager.setLastUpdatedTimestamp()
                        databaseDaoHelper.deleteAllRepos()
                        databaseDaoHelper.insertRepos(items)
                    }
                }
            }

            override fun shouldFetch(data: List<GithubRepo>?): Boolean {
                if (context != null && ConnectivityUtil.isConnected(context) && sharedPreferenceManager.isLocalDataExpired()) {
                    return true
                }
                if (!isDataAvailable(data)) {
                    return true
                }
                return false
            }

            override fun mustFetch(): Boolean = callApiForcefully

            override fun isDataAvailable(data: List<GithubRepo>?): Boolean =
                data != null && data.isNotEmpty()

            override suspend fun loadFromDb(): List<GithubRepo> = withContext(Dispatchers.IO) {
                databaseDaoHelper.getAllRepos()
            }

            override suspend fun createCall(): Resource<List<GithubRepo>> =
                withContext(Dispatchers.IO) {
                    apiServiceHelper.getRepos()
                }
        }.build().asLiveData()
    }

}