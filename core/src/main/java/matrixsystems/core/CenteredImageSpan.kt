package matrixsystems.core

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan
import java.lang.ref.WeakReference

/**
 * Created by Shahbaz Hashmi on 2020-03-07.
 */
class CenteredImageSpan(context: Context?, drawableRes: Int) :
    ImageSpan(context!!, drawableRes) {
    private var mDrawableRef: WeakReference<Drawable?>? =
        null

    override fun getSize(
        paint: Paint, text: CharSequence,
        start: Int, end: Int,
        fm: FontMetricsInt?
    ): Int {
        val d = cachedDrawable
        val rect = d!!.bounds
        if (fm != null) {
            val pfm = paint.fontMetricsInt
            // keep it the same as paint's fm
            fm.ascent = pfm.ascent
            fm.descent = pfm.descent
            fm.top = pfm.top
            fm.bottom = pfm.bottom
        }
        return rect.right
    }

    override fun draw(
        canvas: Canvas, text: CharSequence,
        start: Int, end: Int, x: Float,
        top: Int, y: Int, bottom: Int, paint: Paint
    ) {
        val b = cachedDrawable
        canvas.save()
        val drawableHeight = b!!.intrinsicHeight
        val fontAscent = paint.fontMetricsInt.ascent
        val fontDescent = paint.fontMetricsInt.descent
        val transY = bottom - b.bounds.bottom +  // align bottom to bottom
                (drawableHeight - fontDescent + fontAscent) / 2 // align center to center
        canvas.translate(x, transY.toFloat())
        b.draw(canvas)
        canvas.restore()
    }

    // Redefined locally because it is a private member from DynamicDrawableSpan
    private val cachedDrawable: Drawable?
        get() {
            val wr = mDrawableRef
            var d: Drawable? = null
            if (wr != null) d = wr.get()
            if (d == null) {
                d = drawable
                mDrawableRef = WeakReference(d)
            }
            return d
        }
}