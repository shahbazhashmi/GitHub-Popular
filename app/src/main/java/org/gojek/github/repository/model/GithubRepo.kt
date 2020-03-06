package org.gojek.github.repository.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
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
    @SerializedName("url")
    var url: String = "",
    @SerializedName("author")
    var author: String? = "",
    @SerializedName("avatar")
    var avatar: String? = "",
    /*@SerializedName("builtBy")
    @TypeConverters(BuiltByConverter::class)
    var builtBy: List<BuiltBy?>? = listOf(),*/
    @SerializedName("currentPeriodStars")
    var currentPeriodStars: Int? = 0,
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("forks")
    var forks: Int? = 0,
    @SerializedName("language")
    var language: String? = "",
    @SerializedName("languageColor")
    var languageColor: String? = "",
    @SerializedName("name")
    var name: String? = "",
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
