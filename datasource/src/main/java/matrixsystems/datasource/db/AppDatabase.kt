package matrixsystems.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import matrixsystems.datasource.db.githubrepo.DatabaseDao
import matrixsystems.datasource.model.GithubRepo
import matrixsystems.datasource.model.License
import matrixsystems.datasource.model.Owner

/**
 * Created by Shahbaz Hashmi on 2020-03-04.
 */
@Database(entities = [GithubRepo::class], exportSchema = false, version = 1)
@TypeConverters(BuiltByConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun githubRepoDao(): DatabaseDao

}


/**
 * DataTypeConverter - to save list data locally without typecasting
 */

class BuiltByConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToLicense(data: String?): License? {
        if (data == null) {
            return null
        }
        return gson.fromJson(data, License::class.java)
    }

    @TypeConverter
    fun licenseToString(someObjects: License?): String? {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToOwner(data: String?): Owner? {
        if (data == null) {
            return null
        }
        return gson.fromJson(data, Owner::class.java)
    }

    @TypeConverter
    fun ownerToString(someObjects: Owner?): String? {
        return gson.toJson(someObjects)
    }
}