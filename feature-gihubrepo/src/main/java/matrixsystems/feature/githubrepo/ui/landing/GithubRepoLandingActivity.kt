package matrixsystems.feature.githubrepo.ui.landing

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import matrixsystems.core.extensions.getViewModel
import matrixsystems.core.ui.BaseActivity
import matrixsystems.feature.githubrepo.R
import matrixsystems.feature.githubrepo.databinding.ActivityGithubRepoLandingBinding
import matrixsystems.feature.githubrepo.ui.repolist.RepoListFragment

/**
 * Created by Shahbaz Hashmi on 23/04/21.
 */
class GithubRepoLandingActivity : BaseActivity() {

    private val githubRepoLandingViewModel by lazy {
        getViewModel<GithubRepoLandingViewModel>()
    }

    lateinit var binding: ActivityGithubRepoLandingBinding

    override val contentMain: Int
        get() = R.id.content_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_github_repo_landing)
        binding.vm = githubRepoLandingViewModel
        setSupportActionBar(binding.toolbar.root as Toolbar)
        addFragment(RepoListFragment.getNewInstance(), true)
    }

    fun setAppTitle(titleString: String) {
        title = titleString
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

}