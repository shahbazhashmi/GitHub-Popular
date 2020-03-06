package org.gojek.github.repository.db.githubrepo

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.gojek.github.repository.model.GithubRepo


/**
 * Created by Shahbaz Hashmi on 2020-03-04.
 */
interface GithubRepoDao {

    @Query("SELECT * FROM github_repo")
    fun getAllRepos(): LiveData<List<GithubRepo>>

    @Query("DELETE FROM github_repo")
    fun deleteAllRepos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepo(repo: GithubRepo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repos: List<GithubRepo>)

}