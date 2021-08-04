package matrixsystems.feature.githubrepo.repo

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import matrixsystems.core.constants.SystemVariables
import matrixsystems.feature.githubrepo.data.GithubRepoSharedPreferenceManager
import matrixsystems.feature.githubrepo.data.api.GithubRepoApiServiceHelper
import matrixsystems.datasource.api.network.NetworkAndDBBoundResource
import matrixsystems.feature.githubrepo.data.db.GithubRepoDatabaseDaoHelper
import matrixsystems.datasource.model.Resource
import matrixsystems.feature.githubrepo.BuildConfig.CACHE_TIMEOUT
import matrixsystems.feature.githubrepo.data.models.GithubRepo
import javax.inject.Inject

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
class GithubRepoRepository @Inject constructor(
    private val githubRepoDatabaseDaoHelper: GithubRepoDatabaseDaoHelper,
    private val apiServiceHelper: GithubRepoApiServiceHelper,
    private val githubRepoSharedPreferenceManager: GithubRepoSharedPreferenceManager,
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
                        githubRepoSharedPreferenceManager.setLastUpdatedTimestamp()
                        githubRepoDatabaseDaoHelper.deleteAllRepos()
                        githubRepoDatabaseDaoHelper.insertRepos(items)
                    }
                }
            }

            override fun shouldFetch(data: List<GithubRepo>?): Boolean {
                if (context != null && SystemVariables.isInternetConnected && githubRepoSharedPreferenceManager.isLocalDataExpired(CACHE_TIMEOUT.toLong())) {
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
                githubRepoDatabaseDaoHelper.getAllRepos()
            }

            override suspend fun createCall(): Resource<List<GithubRepo>> =
                withContext(Dispatchers.IO) {
                    apiServiceHelper.getRepos()
                }
        }.build().asLiveData()
    }

}