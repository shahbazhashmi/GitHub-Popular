package org.gojek.github.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.gojek.github.repository.db.githubrepo.GithubRepoDao

/**
 * Created by Shahbaz Hashmi on 2020-03-04.
 */
@Database(entities = [GithubRepoDao::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun githubRepoDao(): GithubRepoDao

    companion object {
        private const val DB_NAME = "github_db"
        private var sInstance: AppDatabase? = null
        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration().build()
            }
            return sInstance
        }
    }
}