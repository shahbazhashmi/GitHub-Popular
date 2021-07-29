package matrixsystems.datasource.api

import matrixsystems.datasource.model.GithubRepo
import matrixsystems.datasource.model.Resource
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
interface ApiService {

    @GET("repos")
    suspend fun getRepos(): Resource<List<GithubRepo>>

}