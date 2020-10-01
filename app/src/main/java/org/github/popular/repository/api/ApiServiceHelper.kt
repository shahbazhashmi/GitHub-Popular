package org.github.popular.repository.api

/**
 * Created by Shahbaz Hashmi on 29/09/20.
 */
class ApiServiceHelper(val apiService: ApiService) {

    suspend fun getRepos(
        language: String = "",
        since: String = "",
        spokenLanguageCode: String = ""
    ) = apiService.getRepos(language, since, spokenLanguageCode)

}