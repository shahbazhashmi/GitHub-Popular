package org.gojek.github.data

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
class GithubRepoRepository(
    private val dao: GithubRepoDao,
    private val remoteDataSource: GithubRepoRemoteDataSource
) {


    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: GithubRepoRepository? = null

        fun getInstance(dao: GithubRepoDao, remoteDataSource: GithubRepoRemoteDataSource) =
            instance ?: synchronized(this) {
                instance
                    ?: GithubRepoRepository(dao, remoteDataSource).also { instance = it }
            }
    }

}