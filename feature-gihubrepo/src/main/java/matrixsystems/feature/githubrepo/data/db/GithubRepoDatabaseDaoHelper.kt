package matrixsystems.feature.githubrepo.data.db

import matrixsystems.feature.githubrepo.data.models.GithubRepo


/**
 * Created by Shahbaz Hashmi on 28/09/20.
 */
class GithubRepoDatabaseDaoHelper(val databaseDao: GithubRepoDatabaseDao) {
    suspend fun getAllRepos() = databaseDao.getAllRepos()
    suspend fun deleteAllRepos() = databaseDao.deleteAllRepos()
    suspend fun insertRepos(repos: List<GithubRepo>) = databaseDao.insertRepos(repos)
}