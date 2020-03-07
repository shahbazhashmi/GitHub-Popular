package org.gojek.github.ui.githubrepo

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import kotlinx.android.synthetic.main.row_github_repo.view.*
import org.gojek.github.R
import org.gojek.github.repository.model.GithubRepo
import org.gojek.github.utils.extensions.inflate

/**
 * Created by Shahbaz Hashmi on 2020-03-07.
 */
class GithubRepoAdapter() : RecyclerView.Adapter<GithubRepoAdapter.RepoHolder>() {

    private var githubRepoList: List<GithubRepo> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepoHolder(parent.inflate(R.layout.row_github_repo))

    override fun getItemCount() = githubRepoList.size

    override fun onBindViewHolder(holder: RepoHolder, position: Int) {
        holder.bind(githubRepoList[position])
    }

    /**
     * View Holder Pattern
     */
    class RepoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * Binds the UI with the data and handles clicks
         */
        fun bind(githubRepo: GithubRepo) = with(itemView) {
            imageview_profile.load(githubRepo.avatar)
        }
    }

    /**
     * Swap function to set new data on updating
     */
    fun setData(items: List<GithubRepo>) {
        githubRepoList = items
        notifyDataSetChanged()
    }

}