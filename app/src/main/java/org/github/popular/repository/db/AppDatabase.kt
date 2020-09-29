package org.github.popular.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.github.popular.repository.db.githubrepo.GithubRepoDao
import org.github.popular.repository.model.BuiltBy
import org.github.popular.repository.model.GithubRepo
import java.lang.reflect.Type
import java.util.*

/**
 * Created by Shahbaz Hashmi on 2020-03-04.
 */
@Database(entities = [GithubRepo::class], exportSchema = false, version = 1)
@TypeConverters(BuiltByConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun githubRepoDao(): GithubRepoDao

}


/**
 * DataTypeConverter - to save list data locally without typecasting
 */

class BuiltByConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<BuiltBy?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type =
            object : TypeToken<List<BuiltBy?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun ListToString(someObjects: List<BuiltBy?>?): String? {
        return gson.toJson(someObjects)
    }
}