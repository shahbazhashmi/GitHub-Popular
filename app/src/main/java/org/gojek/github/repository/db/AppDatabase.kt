package org.gojek.github.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.gojek.github.repository.db.githubrepo.GithubRepoDao
import org.gojek.github.repository.model.GithubRepo

/**
 * Created by Shahbaz Hashmi on 2020-03-04.
 */
@Database(entities = [GithubRepo::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun githubRepoDao(): GithubRepoDao

}