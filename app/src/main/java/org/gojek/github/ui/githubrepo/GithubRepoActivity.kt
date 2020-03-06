package org.gojek.github.ui.githubrepo

import android.os.Bundle
import androidx.lifecycle.observe
import org.gojek.github.R
import org.gojek.github.ui.BaseActivity
import org.gojek.github.utils.extensions.getViewModel

class GithubRepoActivity : BaseActivity() {

    private val githubRepoViewModel by lazy {
        getViewModel<GithubRepoViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_repo)
        getGithubRepos()
    }

    private fun getGithubRepos() {
        /*
        * Observing for data change, Cater DB and Network Both
        * */
        githubRepoViewModel.getGithubReposFromServer().observe(this) {
            when {
                it.status.isLoading() -> {
                    //news_list.showProgressView()
                }
                it.status.isSuccessful() -> {
                    /*it.load(news_list) {
                        // Update the UI as the data has changed
                        it?.let { adapter.replaceItems(it) }
                    }*/
                }
                it.status.isError() -> {
                    /*if (it.errorMessage != null)
                        ToastUtil.showCustomToast(this, it.errorMessage.toString())*/
                }
            }
        }
    }
}
