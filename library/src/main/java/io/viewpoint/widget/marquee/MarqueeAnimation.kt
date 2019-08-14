package io.viewpoint.widget.marquee

import android.graphics.Rect
import android.view.animation.LinearInterpolator
import android.widget.Scroller
import android.widget.TextView
import java.util.concurrent.TimeUnit

class MarqueeAnimation(val textView: TextView) {
    private val scroller: Scroller = Scroller(textView.context, LinearInterpolator())

    init {
        textView.setScroller(scroller)
    }

    private val textWidth: Int
        get() {
            val text = textView.text.toString()
            val textPaint = textView.paint
            val bounds = Rect()
            textPaint.getTextBounds(text, 0, text.length, bounds)
            return bounds.width()
        }

    fun startAnimation() {
        with(textView) {
            post {
                val tvWidth = width
                val totalWidthPadding = paddingStart + paddingEnd
                val moreSpacing = 2.toDp
                scroller.startScroll(
                    0,
                    0,
                    (textWidth + moreSpacing) - (tvWidth - totalWidthPadding),
                    0,
                    TimeUnit.SECONDS.toMillis(2).toInt()
                )
                invalidate()
            }
        }
    }
}