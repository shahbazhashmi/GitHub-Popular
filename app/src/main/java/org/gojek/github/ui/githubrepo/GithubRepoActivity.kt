package org.gojek.github.ui.githubrepo

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import org.gojek.github.R
import org.gojek.github.databinding.ActivityGithubRepoBinding
import org.gojek.github.ui.BaseActivity
import org.gojek.github.utils.extensions.getViewModel

class GithubRepoActivity : BaseActivity() {

    private val TAG = "GithubRepoActivity"

    private val githubRepoViewModel by lazy {
        getViewModel<GithubRepoViewModel>()
    }

    lateinit var binding : ActivityGithubRepoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_github_repo)
        binding.vm = githubRepoViewModel
        githubRepoViewModel.loaderHelper.setRetryListener {
            getGithubRepos()
        }
        getGithubRepos()
    }

    private fun getGithubRepos() {
        /*
        * Observing for data change, Cater DB and Network Both
        * */
        githubRepoViewModel.getGithubRepos().observe(this) {
            when {
                it.status.isLoading() -> {
                    githubRepoViewModel.loaderHelper.showLoading()
                }
                it.status.isSuccessful() -> {
                    Log.d(TAG, "success")
                    githubRepoViewModel.loaderHelper.dismiss()
                    /*it.load(news_list) {
                        // Update the UI as the data has changed
                        it?.let { adapter.replaceItems(it) }
                    }*/
                }
                it.status.isError() -> {
                    Log.d(TAG, it.errorMessage.toString())
                    githubRepoViewModel.loaderHelper.showError(getString(R.string.txt_something_went_wrong), getString(R.string.txt_alien_blocking_signal))
                    /*if (it.errorMessage != null)
                        ToastUtil.showCustomToast(this, it.errorMessage.toString())*/
                }
            }
        }
    }
}
