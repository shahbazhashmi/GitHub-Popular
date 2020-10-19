package matrixsystems.datasource.api

/**
 * Created by Shahbaz Hashmi on 29/09/20.
 */
class ApiServiceHelper(val apiService: ApiService) {

    suspend fun getRepos() = apiService.getRepos()

}