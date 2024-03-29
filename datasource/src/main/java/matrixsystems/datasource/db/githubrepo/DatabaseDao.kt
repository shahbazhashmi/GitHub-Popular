package matrixsystems.datasource.db.githubrepo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import matrixsystems.datasource.model.GithubRepo


/**
 * Created by Shahbaz Hashmi on 2020-03-04.
 */
@Dao
interface DatabaseDao {

    @Query("SELECT COUNT(*) FROM github_repo")
    suspend fun getRepoCount(): Int

    @Query("SELECT * FROM github_repo")
    suspend fun getAllRepos(): List<GithubRepo>

    @Query("DELETE FROM github_repo")
    suspend fun deleteAllRepos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo: GithubRepo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<GithubRepo>): List<Long>

}