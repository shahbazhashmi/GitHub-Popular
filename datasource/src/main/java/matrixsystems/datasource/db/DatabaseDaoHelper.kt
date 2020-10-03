package matrixsystems.datasource.db

import matrixsystems.datasource.db.githubrepo.DatabaseDao
import matrixsystems.datasource.model.GithubRepo


/**
 * Created by Shahbaz Hashmi on 28/09/20.
 */
class DatabaseDaoHelper(val databaseDao: DatabaseDao) {
    suspend fun getAllRepos() = databaseDao.getAllRepos()
    suspend fun deleteAllRepos() = databaseDao.deleteAllRepos()
    suspend fun insertRepos(repos: List<GithubRepo>) = databaseDao.insertRepos(repos)
}