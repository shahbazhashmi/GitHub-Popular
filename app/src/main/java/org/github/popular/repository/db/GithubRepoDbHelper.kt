package org.github.popular.repository.db

import org.github.popular.repository.db.githubrepo.GithubRepoDao
import org.github.popular.repository.model.GithubRepo

/**
 * Created by Shahbaz Hashmi on 28/09/20.
 */
class GithubRepoDbHelper(val githubRepoDao: GithubRepoDao) {
    suspend fun getAllRepos() = githubRepoDao.getAllRepos()
    suspend fun deleteAllRepos() = githubRepoDao.deleteAllRepos()
    suspend fun insertRepos(repos: List<GithubRepo>) = githubRepoDao.insertRepos(repos)
}