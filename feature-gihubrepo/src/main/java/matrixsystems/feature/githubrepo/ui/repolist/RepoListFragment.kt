package matrixsystems.feature.githubrepo.ui.repolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import matrixsystems.core.extensions.getViewModel
import matrixsystems.core.ui.BaseFragment
import matrixsystems.core.utils.CoreUtil
import matrixsystems.feature.githubrepo.R
import matrixsystems.feature.githubrepo.databinding.FragmentRepoListBinding
import matrixsystems.feature.githubrepo.ui.landing.GithubRepoLandingActivity
import javax.inject.Inject


class RepoListFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val LIST_POSITION = "list_position"

    private val TAG = "RepoListFragment"

    private val githubRepoViewModel by lazy {
        getViewModel<RepoListViewModel>()
    }

    private val recyclerViewLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    lateinit var binding: FragmentRepoListBinding

    @Inject
    lateinit var coreUtil: CoreUtil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_repo_list, container, false)
        binding.vm = githubRepoViewModel
        binding.lifecycleOwner = this
        (activity as GithubRepoLandingActivity).setAppTitle(getString(R.string.txt_trending))
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
        return binding.root
    }


    private fun getGithubRepos(callApiForcefully: Boolean = false) {
        githubRepoViewModel.fetchGithubRepos(callApiForcefully)
    }

    private fun attachDataChangeListener() {
        githubRepoViewModel.repos.observe(viewLifecycleOwner, Observer {
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
                            requireActivity(),
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


    companion object {
        fun getNewInstance() = RepoListFragment()
    }
}
