package org.gojek.github.ui.githubrepo

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.gojek.github.R
import org.gojek.github.databinding.ActivityGithubRepoBinding
import org.gojek.github.ui.BaseActivity
import org.gojek.github.utils.AppUtil
import org.gojek.github.utils.extensions.getViewModel


class GithubRepoActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val TAG = "GithubRepoActivity"

    private val githubRepoViewModel by lazy {
        getViewModel<GithubRepoViewModel>()
    }

    lateinit var binding : ActivityGithubRepoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_github_repo)
        binding.vm = githubRepoViewModel
        binding.swipeContainer.setOnRefreshListener(this)
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
                    if (it.data == null || it.data!!.isEmpty()) {
                        githubRepoViewModel.loaderHelper.showError(
                            getString(R.string.txt_data_not_found),
                            getString(R.string.txt_try_again_later)
                        )
                    } else {
                        githubRepoViewModel.loaderHelper.dismiss()
                        githubRepoViewModel.githubRepoAdapter.setData(it.data!!)
                    }
                }
                it.status.isError() -> {
                    Log.d(TAG, it.errorMessage.toString())
                    githubRepoViewModel.loaderHelper.showError(getString(R.string.txt_something_went_wrong), getString(R.string.txt_alien_blocking_signal))
                }
            }
        }
    }


    private fun getGithubReposFromServer() {
        /*
        * Observing for data change from Network
        * */
        githubRepoViewModel.getGithubReposFromServer().observe(this) {
            when {
                it.status.isLoading() -> {
                    binding.swipeContainer.isRefreshing = true
                }
                it.status.isSuccessful() -> {
                    Log.d(TAG, "success")
                    binding.swipeContainer.isRefreshing = false
                    if (it.data == null || it.data!!.isEmpty()) {
                        AppUtil.showToast(
                            this,
                            "${getString(R.string.txt_data_not_found)} - ${getString(R.string.txt_try_again_later)}",
                            false
                        )
                    } else {
                        githubRepoViewModel.loaderHelper.dismiss()
                        githubRepoViewModel.githubRepoAdapter.setData(it.data!!)
                    }
                }
                it.status.isError() -> {
                    binding.swipeContainer.isRefreshing = false
                    Log.d(TAG, it.errorMessage.toString())
                    AppUtil.showToast(
                        this,
                        "${getString(R.string.txt_something_went_wrong)} - ${getString(R.string.txt_alien_blocking_signal)}",
                        false
                    )
                }
            }
        }
    }


    override fun onRefresh() {
        getGithubReposFromServer()
    }
}
