package org.gojek.github.repository.repo

import android.content.Context
import androidx.lifecycle.LiveData
import org.gojek.github.app.AppExecutors
import org.gojek.github.repository.api.ApiService
import org.gojek.github.repository.api.network.NetworkAndDBBoundResource
import org.gojek.github.repository.api.network.NetworkResource
import org.gojek.github.repository.api.network.Resource
import org.gojek.github.repository.db.githubrepo.GithubRepoDao
import org.gojek.github.repository.model.GithubRepo
import org.gojek.github.utils.ConnectivityUtil
import javax.inject.Inject

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
class GithubRepoRepository @Inject constructor(
    private val githubRepoDao: GithubRepoDao,
    private val apiService: ApiService,
    private val context: Context,
    private val appExecutors: AppExecutors = AppExecutors()
) {

    /**
     * Fetch the repos from database if exist else fetch from web
     * and persist them in the database
     */
    fun getGithubRepos(): LiveData<Resource<List<GithubRepo>?>> {

        return object :
            NetworkAndDBBoundResource<List<GithubRepo>, List<GithubRepo>>(appExecutors) {
            override fun saveCallResult(item: List<GithubRepo>) {
                if (!item.isEmpty()) {
                    githubRepoDao.deleteAllRepos()
                    githubRepoDao.insertRepos(item)
                }
            }

            override fun shouldFetch(data: List<GithubRepo>?) =
                (ConnectivityUtil.isConnected(context))

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