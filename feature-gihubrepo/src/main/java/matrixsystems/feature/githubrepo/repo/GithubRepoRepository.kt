package matrixsystems.feature.githubrepo.repo

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import matrixsystems.core.SharedPreferenceManager
import matrixsystems.core.utils.ConnectivityUtil
import matrixsystems.datasource.BuildConfig
import matrixsystems.datasource.api.ApiServiceHelper
import matrixsystems.datasource.api.network.NetworkAndDBBoundResource
import matrixsystems.datasource.api.network.Resource
import matrixsystems.datasource.db.DatabaseDaoHelper
import matrixsystems.datasource.model.GithubRepo
import javax.inject.Inject

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
internal class GithubRepoRepository @Inject constructor(
    private val databaseDaoHelper: DatabaseDaoHelper,
    private val apiServiceHelper: ApiServiceHelper,
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val connectivityUtil: ConnectivityUtil,
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
                if (context != null && connectivityUtil.isConnected(context) && sharedPreferenceManager.isLocalDataExpired(BuildConfig.CACHE_TIMEOUT.toLong())) {
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