package org.gojek.github.api

import org.gojek.github.data.GithubRepo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
interface GithubRepoService {

    @GET("repositories")
    suspend fun getRepos(
        @Query("language") language: String,
        @Query("since") since: String,
        @Query("spoken_language_code") spokenLanguageCode: String
    ): Response<List<GithubRepo>>

}