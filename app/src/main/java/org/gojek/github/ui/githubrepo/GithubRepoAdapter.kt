package org.gojek.github.ui.githubrepo

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import kotlinx.android.synthetic.main.row_github_repo.view.*
import org.gojek.github.R
import org.gojek.github.repository.model.GithubRepo
import org.gojek.github.utils.CenteredImageSpan
import org.gojek.github.utils.extensions.inflate
import javax.inject.Inject


/**
 * Created by Shahbaz Hashmi on 2020-03-07.
 */
class GithubRepoAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<GithubRepoAdapter.RepoHolder>() {

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
    inner class RepoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * Binds the UI with the data and handles clicks
         */
        fun bind(githubRepo: GithubRepo) = with(itemView) {
            imageview_profile.load(githubRepo.avatar)
            textview_username.setText(githubRepo.author)
            textview_reponame.setText(githubRepo.name)
            if (!TextUtils.isEmpty(githubRepo.description)) {
                textview_description.setText(githubRepo.description)
                textview_description.visibility = View.VISIBLE
            } else {
                textview_description.visibility = View.GONE
            }

            var languageSpannable: SpannableString? = null
            var starSpannable: SpannableString? = null
            var forkSpannable: SpannableString? = null

            if (!TextUtils.isEmpty(githubRepo.language) && !TextUtils.isEmpty(githubRepo.languageColor)) {
                languageSpannable = SpannableString("\u25CF ${githubRepo.language}")
                languageSpannable.setSpan(
                    ForegroundColorSpan(Color.parseColor(githubRepo.languageColor)),
                    0,
                    languageSpannable.length - githubRepo.language!!.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

            }

            if (githubRepo.stars != null) {
                starSpannable =
                    getAttributeSpannableString(githubRepo.stars!!, R.drawable.star_yellow_16)
            }

            if (githubRepo.forks != null) {
                forkSpannable =
                    getAttributeSpannableString(githubRepo.forks!!, R.drawable.fork_black_16)
            }

            if (languageSpannable != null && starSpannable != null && forkSpannable != null) {
                textview_attributes.visibility = View.VISIBLE
                textview_attributes.setText(
                    TextUtils.concat(
                        languageSpannable,
                        starSpannable,
                        forkSpannable
                    )
                )
            } else {
                textview_attributes.visibility = View.GONE
            }

        }
    }

    fun getAttributeSpannableString(count: Int, res: Int): SpannableString {
        // 3 blank space beacuse it is needed for the image
        val spannableString = SpannableString("      $count")
        spannableString.setSpan(
            CenteredImageSpan(context, res),
            3,
            6,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannableString

    }


    /**
     * Swap function to set new data on updating
     */
    fun setData(items: List<GithubRepo>) {
        githubRepoList = items
        notifyDataSetChanged()
    }

}