package org.github.popular.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import matrixsystems.core.extensions.getViewModel
import matrixsystems.core.ui.BaseActivity
import matrixsystems.feature.githubrepo.ui.landing.GithubRepoLandingActivity
import org.github.popular.R
import org.github.popular.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private val mainViewModel by lazy {
        getViewModel<MainViewModel>()
    }

    lateinit var binding: ActivityMainBinding

    override val contentMain: Int?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = mainViewModel
        binding.lifecycleOwner = this

        val intent = Intent(this, GithubRepoLandingActivity::class.java)
        startActivity(intent)
        finish()
    }
}