package matrixsystems.feature.githubrepo.data.api

/**
 * Created by Shahbaz Hashmi on 29/09/20.
 */
class GithubRepoApiServiceHelper(val githubRepoApiService: GithubRepoApiService) {

    suspend fun getRepos() = githubRepoApiService.getRepos()

}