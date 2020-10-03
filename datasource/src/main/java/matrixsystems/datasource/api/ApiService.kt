package matrixsystems.datasource.api

import matrixsystems.core.model.Resource
import matrixsystems.datasource.model.GithubRepo
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