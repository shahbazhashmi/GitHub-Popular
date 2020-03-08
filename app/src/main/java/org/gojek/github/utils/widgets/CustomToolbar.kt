package org.gojek.github.utils.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import org.gojek.github.R

/**
 * Created by Shahbaz Hashmi on 2020-03-08.
 */
class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.toolbarStyle
) : Toolbar(context, attrs, defStyleAttr) {
    private val titleView: TextView
    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        super.onLayout(changed, l, t, r, b)
        titleView.x = (width - titleView.width) / 2.toFloat()
    }

    override fun setTitle(title: CharSequence) {
        titleView.text = title
    }

    init {
        titleView = TextView(getContext())
        val textAppearanceStyleResId: Int
        val a = context.theme.obtainStyledAttributes(
            attrs,
            intArrayOf(androidx.appcompat.R.attr.titleTextAppearance),
            defStyleAttr,
            0
        )
        textAppearanceStyleResId = try {
            a.getResourceId(0, 0)
        } finally {
            a.recycle()
        }
        if (textAppearanceStyleResId > 0) {
            titleView.setTextAppearance(context, textAppearanceStyleResId)
        }
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        titleView.setTextColor(resources.getColor(R.color.app_black))
        addView(
            titleView,
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        )
    }
}