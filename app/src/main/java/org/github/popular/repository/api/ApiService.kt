package org.github.popular.repository.api

import androidx.lifecycle.LiveData
import org.github.popular.repository.api.network.Resource
import org.github.popular.repository.model.GithubRepo
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
interface ApiService {

    @GET("repositories")
    suspend fun getRepos(
        @Query("language") language: String = "",
        @Query("since") since: String = "",
        @Query("spoken_language_code") spokenLanguageCode: String = ""
    ): Resource<List<GithubRepo>>

}