package org.gojek.github.data

import org.gojek.github.api.BaseDataSource
import org.gojek.github.api.GithubRepoService

/**
 * Created by Shahbaz Hashmi on 2020-03-05.
 */
class GithubRepoRemoteDataSource(private val githubRepoService: GithubRepoService) :
    BaseDataSource() {

    suspend fun fetchRepos(
        language: String = "",
        since: String = "",
        spokenLanguageCode: String = ""
    ) = getResult<GithubRepo> {
        githubRepoService.getRepos(language, since, spokenLanguageCode)
    }

}