package org.gojek.github.repository.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Shahbaz Hashmi on 2020-03-06.
 */
data class GithubRepoSource(
    @SerializedName("source") var repos: List<GithubRepo> = emptyList()
)