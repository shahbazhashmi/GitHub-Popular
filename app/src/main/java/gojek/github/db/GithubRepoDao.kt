package gojek.github.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


/**
 * Created by Shahbaz Hashmi on 2020-03-04.
 */
interface GithubRepoDao {

    @Query("SELECT * FROM github_repo")
    fun getAllGithubRepos(): List<GithubRepo?>?

    @Query("DELETE FROM github_repo")
    fun deleteAllGithubRepos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGithubRepo(repo: GithubRepo?)

}