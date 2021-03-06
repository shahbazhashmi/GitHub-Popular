package matrixsystems.feature.githubrepo.ui.repolist

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import matrixsystems.core.utils.CoreUtil
import matrixsystems.core.extensions.getViewModel
import matrixsystems.core.ui.BaseActivity
import matrixsystems.core.widgets.CustomToolbar
import matrixsystems.feature.githubrepo.R
import matrixsystems.feature.githubrepo.databinding.ActivityRepoListBinding
import javax.inject.Inject


class RepoListActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val LIST_POSITION = "list_position"

    private val TAG = "RepoListActivity"

    private val githubRepoViewModel by lazy {
        getViewModel<RepoListViewModel>()
    }

    private val recyclerViewLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    lateinit var binding: ActivityRepoListBinding

    @Inject
    lateinit var coreUtil: CoreUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_list)
        binding.vm = githubRepoViewModel
        setSupportActionBar(binding.toolbar.root as Toolbar)
        title = getString(R.string.txt_trending)
        binding.swipeContainer.setOnRefreshListener(this)
        binding.recyclerviewRepo.layoutManager = recyclerViewLayoutManager
        if (savedInstanceState != null) {
            // scroll to existing position which exist before rotation.
            binding.recyclerviewRepo.scrollToPosition(savedInstanceState.getInt(LIST_POSITION))
            // set selected position
            githubRepoViewModel.repoListAdapter.selectedPosition =
                savedInstanceState.getInt(githubRepoViewModel.repoListAdapter.SELECTED_LIST_POSITION)
        }
        attachDataChangeListener()
        githubRepoViewModel.loaderHelper.setRetryListener {
            getGithubRepos()
        }
        getGithubRepos()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    private fun getGithubRepos(callApiForcefully: Boolean = false) {
        githubRepoViewModel.fetchGithubRepos(callApiForcefully)
    }

    private fun attachDataChangeListener() {
        githubRepoViewModel.repos.observe(this, Observer {
            when {
                it!!.status.isLoading() -> {
                    if (!binding.swipeContainer.isRefreshing) {
                        githubRepoViewModel.loaderHelper.showLoading()
                    }
                }
                it.status.isSuccessful() -> {
                    Log.d(TAG, "data success")
                    if (it.data == null || it.data!!.isEmpty()) {
                        githubRepoViewModel.loaderHelper.showError(
                            getString(R.string.txt_data_not_found),
                            getString(R.string.txt_try_again_later)
                        )
                    } else {
                        if (binding.swipeContainer.isRefreshing) {
                            binding.swipeContainer.isRefreshing = false
                        } else {
                            githubRepoViewModel.loaderHelper.dismiss()
                        }
                        githubRepoViewModel.repoListAdapter.setData(it.data!!)
                    }
                }
                it.status.isError() -> {
                    Log.d(TAG, it.errorMessage.toString())
                    if (binding.swipeContainer.isRefreshing) {
                        binding.swipeContainer.isRefreshing = false
                        coreUtil.showToast(
                            this,
                            "${getString(R.string.txt_something_went_wrong)} - ${getString(R.string.txt_alien_blocking_signal)}",
                            false
                        )
                    } else {
                        githubRepoViewModel.loaderHelper.showError(
                            getString(R.string.txt_something_went_wrong),
                            getString(R.string.txt_alien_blocking_signal)
                        )
                    }
                }
            }
        })
    }

    override fun onRefresh() {
        binding.swipeContainer.isRefreshing = true
        getGithubRepos(true)
    }


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt(
            LIST_POSITION,
            recyclerViewLayoutManager.findFirstVisibleItemPosition()
        ) // get current recycle view position here.
        // selected / expanded positon.
        savedInstanceState.putInt(
            githubRepoViewModel.repoListAdapter.SELECTED_LIST_POSITION,
            githubRepoViewModel.repoListAdapter.selectedPosition
        ) // get current recycle view selected position here.
        super.onSaveInstanceState(savedInstanceState)
    }
}
