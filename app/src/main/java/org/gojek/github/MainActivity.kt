package org.gojek.github

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.gojek.github.data.GithubRepoDao
import org.gojek.github.data.GithubRepoRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //GithubRepoRepository.getInstance(GithubRepoDao)
    }
}
