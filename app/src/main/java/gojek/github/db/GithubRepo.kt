package gojek.github.db

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Type
import java.util.*


/**
 * Created by Shahbaz Hashmi on 2020-03-04.
 */
@SuppressLint("ParcelCreator")
@Parcelize
@Entity(tableName = "github_repo")
data class GithubRepo(
    @PrimaryKey
    @ColumnInfo(name = "url")
    @SerializedName("url")
    var url: String = "",
    @ColumnInfo(name = "author")
    @SerializedName("author")
    var author: String? = "",
    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    var avatar: String? = "",
    @ColumnInfo(name = "builtBy")
    @SerializedName("builtBy")
    @TypeConverters(BuiltByConverter::class)
    var builtBy: List<BuiltBy?>? = listOf(),
    @ColumnInfo(name = "currentPeriodStars")
    @SerializedName("currentPeriodStars")
    var currentPeriodStars: Int? = 0,
    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description: String? = "",
    @ColumnInfo(name = "forks")
    @SerializedName("forks")
    var forks: Int? = 0,
    @ColumnInfo(name = "language")
    @SerializedName("language")
    var language: String? = "",
    @ColumnInfo(name = "languageColor")
    @SerializedName("languageColor")
    var languageColor: String? = "",
    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String? = "",
    @ColumnInfo(name = "stars")
    @SerializedName("stars")
    var stars: Int? = 0
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class BuiltBy(
    @SerializedName("avatar")
    var avatar: String? = "",
    @SerializedName("href")
    var href: String? = "",
    @SerializedName("username")
    var username: String? = ""
) : Parcelable

/**
 * DataTypeConverter - to save list data locally without typecasting
 */
object BuiltByConverter {
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