package matrixsystems.core.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import matrixsystems.core.R

/**
 * Created by Shahbaz Hashmi on 2020-03-08.
 */
class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.toolbarStyle
) : Toolbar(context, attrs, defStyleAttr) {
    private val titleView: TextView = TextView(getContext())
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
            TextViewCompat.setTextAppearance(titleView, textAppearanceStyleResId)
        }
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        titleView.setTextColor(ContextCompat.getColor(context, R.color.app_black))
        addView(
            titleView,
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        )
    }
}