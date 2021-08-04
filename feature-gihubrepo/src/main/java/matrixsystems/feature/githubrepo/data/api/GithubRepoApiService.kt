package matrixsystems.feature.githubrepo.data.api

import matrixsystems.datasource.model.Resource
import matrixsystems.feature.githubrepo.data.models.GithubRepo
import retrofit2.http.GET

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
interface GithubRepoApiService {

    @GET("repos")
    suspend fun getRepos(): Resource<List<GithubRepo>>

}