package org.gojek.github.data

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


/**
 * Created by Shahbaz Hashmi on 2020-03-04.
 */
interface GithubRepoDao {

    @Query("SELECT * FROM github_repo")
    fun getAllGithubRepos(): LiveData<List<GithubRepo?>?>

    @Query("DELETE FROM github_repo")
    fun deleteAllGithubRepos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGithubRepo(repo: GithubRepo?)

}