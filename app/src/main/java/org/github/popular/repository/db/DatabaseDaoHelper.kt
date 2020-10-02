package org.github.popular.repository.db

import org.github.popular.repository.db.githubrepo.databaseDao
import org.github.popular.repository.model.GithubRepo

/**
 * Created by Shahbaz Hashmi on 28/09/20.
 */
class DatabaseDaoHelper(val databaseDao: databaseDao) {
    suspend fun getAllRepos() = databaseDao.getAllRepos()
    suspend fun deleteAllRepos() = databaseDao.deleteAllRepos()
    suspend fun insertRepos(repos: List<GithubRepo>) = databaseDao.insertRepos(repos)
}