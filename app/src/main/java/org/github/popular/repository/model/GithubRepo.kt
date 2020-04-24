package org.github.popular.repository.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


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
    @SerializedName("builtBy")
    var builtBy: List<BuiltBy?>? = listOf(),
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
