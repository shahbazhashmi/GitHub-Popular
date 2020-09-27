package org.github.popular.repository.repo

import android.content.Context
import androidx.lifecycle.LiveData
import org.github.popular.app.AppExecutors
import org.github.popular.repository.api.ApiService
import org.github.popular.repository.api.network.NetworkAndDBBoundResource
import org.github.popular.repository.api.network.NetworkResource
import org.github.popular.repository.api.network.Resource
import org.github.popular.repository.db.githubrepo.GithubRepoDao
import org.github.popular.repository.model.GithubRepo
import org.github.popular.utils.ConnectivityUtil
import org.github.popular.utils.SharedPreferenceManager
import javax.inject.Inject

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
class GithubRepoRepository @Inject constructor(
    private val githubRepoDao: GithubRepoDao,
    private val apiService: ApiService,
    private val context: Context,
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val appExecutors: AppExecutors = AppExecutors()
) {

    /**
     * Fetch the repos from database if exist else fetch from web
     * and persist them in the database
     */
    fun getGithubRepos(callApiForcefully: Boolean): LiveData<Resource<List<GithubRepo>?>> {

        return object :
            NetworkAndDBBoundResource<List<GithubRepo>, List<GithubRepo>>(appExecutors) {
            override fun saveCallResult(item: List<GithubRepo>) {
                if (item.isNotEmpty()) {
                    sharedPreferenceManager.setLastUpdatedTimestamp()
                    githubRepoDao.deleteAllRepos()
                    githubRepoDao.insertRepos(item)
                }
            }

            override fun mustFetch(data: List<GithubRepo>?) = callApiForcefully

            override fun shouldFetch(data: List<GithubRepo>?): Boolean {
                if (ConnectivityUtil.isConnected(context) && sharedPreferenceManager.isLocalDataExpired()) {
                    return true
                }
                return false
            }

            override fun loadFromDb() = githubRepoDao.getAllRepos()

            override fun createCall() =
                apiService.getRepos()

        }.asLiveData()
    }

    /**
     * Fetch the repos from database if exist else fetch from web
     * and persist them in the database
     * LiveData<Resource<githubRepoSource>>
     */
    fun getGithubReposFromServerOnly():
            LiveData<Resource<List<GithubRepo>>> {

        return object : NetworkResource<List<GithubRepo>>() {
            override fun createCall(): LiveData<Resource<List<GithubRepo>>> {
                return apiService.getRepos()
            }

        }.asLiveData()
    }

}