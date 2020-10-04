package matrixsystems.core.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Shahbaz Hashmi on 2020-03-07.
 */
object BindingUtil {
    const val ADAPTER = "recyclerViewAdapter"
    private const val VISIBILITY = "android:visibility"
    @JvmStatic
    @BindingAdapter(VISIBILITY)
    fun setVisibility(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(ADAPTER)
    fun bindRecyclerAdapter(
        recyclerView: RecyclerView,
        recyclerViewAdapter: RecyclerView.Adapter<*>?
    ) {
        recyclerView.adapter = recyclerViewAdapter
    }
}