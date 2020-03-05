package gojek.github.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Shahbaz Hashmi on 2020-03-04.
 */
@Database(entities = [GithubRepoDao::class], exportSchema = false, version = 1)
abstract class GithubRepoDatabase : RoomDatabase() {

    abstract fun githubRepoDao(): GithubRepoDao

    companion object {
        private const val DB_NAME = "station_db"
        private var sInstance: GithubRepoDatabase? = null
        @Synchronized
        fun getInstance(context: Context): GithubRepoDatabase? {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubRepoDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration().build()
            }
            return sInstance
        }
    }
}