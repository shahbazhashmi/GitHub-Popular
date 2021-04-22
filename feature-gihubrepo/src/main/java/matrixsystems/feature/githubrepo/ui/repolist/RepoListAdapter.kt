package matrixsystems.feature.githubrepo.ui.repolist

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.row_github_repo.view.*
import matrixsystems.core.CenteredImageSpan
import matrixsystems.core.extensions.autoNotify
import matrixsystems.core.extensions.inflate
import matrixsystems.datasource.model.GithubRepo
import matrixsystems.feature.githubrepo.R
import javax.inject.Inject
import kotlin.properties.Delegates


/**
 * Created by Shahbaz Hashmi on 2020-03-07.
 */
class RepoListAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<RepoListAdapter.RepoHolder>() {

    private val TAG = "GithubRepoAdapter"

    val SELECTED_LIST_POSITION = "selected_list_position"
    val SELECT_RESET_VALUE = -5

    private var githubRepoList: List<GithubRepo> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.url == n.url }
    }

    var selectedPosition: Int = SELECT_RESET_VALUE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepoHolder(parent.inflate(R.layout.row_github_repo))

    override fun getItemCount() = githubRepoList.size

    override fun onBindViewHolder(holder: RepoHolder, position: Int) {
        holder.bind(position)
    }

    /**
     * View Holder Pattern
     */
    inner class RepoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * Binds the UI with the data and handles clicks
         */
        fun bind(position: Int) = with(itemView) {
            val isExpanded = selectedPosition == position
            val githubRepo = githubRepoList[position]
            imageview_profile.load(githubRepo.owner?.avatarUrl)
            textview_username.text = githubRepo.fullName
            textview_reponame.text = githubRepo.name

            itemView.setOnClickListener {
                if (selectedPosition == SELECT_RESET_VALUE) {
                    selectedPosition = position
                } else if (selectedPosition == position) {
                    selectedPosition = SELECT_RESET_VALUE
                } else {
                    selectedPosition = position
                }

                notifyDataSetChanged()
            }



            if (isExpanded) {

                if (!TextUtils.isEmpty(githubRepo.description)) {
                    textview_description.text = githubRepo.description
                    textview_description.visibility = View.VISIBLE
                } else {
                    textview_description.visibility = View.GONE
                }

                var languageSpannable: SpannableString? = null
                var starSpannable: SpannableString? = null
                var forkSpannable: SpannableString? = null

                if (!TextUtils.isEmpty(githubRepo.language)) {
                    languageSpannable = SpannableString("\u25CF ${githubRepo.language}")
                    try {
                        languageSpannable.setSpan(
                            ForegroundColorSpan(Color.parseColor("#000000")),
                            0,
                            languageSpannable.length - githubRepo.language!!.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    } catch (e: Exception) {
                        Log.e(TAG, e.toString())
                    }

                }

                if (githubRepo.stargazersCount != null) {
                    starSpannable =
                        getAttributeSpannableString(
                            githubRepo.stargazersCount!!,
                            R.drawable.star_yellow_16
                        )
                }

                if (githubRepo.forks != null) {
                    forkSpannable =
                        getAttributeSpannableString(githubRepo.forks!!, R.drawable.fork_black_16)
                }

                if (languageSpannable != null || starSpannable != null || forkSpannable != null) {
                    textview_attributes.visibility = View.VISIBLE
                    textview_attributes.text = TextUtils.concat(
                        languageSpannable ?: "",
                        starSpannable ?: "",
                        forkSpannable ?: ""
                    )
                } else {
                    textview_attributes.visibility = View.GONE
                }
            } else {
                textview_description.visibility = View.GONE
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
        // don't reset selection if same list found
        if (githubRepoList != items) {
            selectedPosition = SELECT_RESET_VALUE
        }
        githubRepoList = items
        notifyDataSetChanged()
    }

}