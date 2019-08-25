package io.viewpoint.widget.marquee

import android.content.Context
import android.graphics.Rect
import android.util.LayoutDirection
import android.util.Log
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.Scroller
import android.widget.TextView
import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import java.util.concurrent.TimeUnit

fun log(msg: String) {
    Log.d("Marquee", msg)
}

class MarqueeAnimation(
    private val textView: TextView,
    private val delayMilliseconds: Long,
    private val durationMilliseconds: Int
) {
    private class CompletableScroller(
        context: Context,
        interpolator: Interpolator
    ) : Scroller(context, interpolator) {
        private val completeSignal = CompletableSubject.create()

        override fun computeScrollOffset(): Boolean {
            val result = super.computeScrollOffset()
            log("computeScrollOffset : $isFinished, result : $result")
            if (isFinished && duration > 0) {
                completeSignal.onComplete()
            }
            return result
        }

        fun toCompletable(): Completable = completeSignal.hide()
    }

    private val scroller = CompletableScroller(textView.context, LinearInterpolator())

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

    private val textViewWidthWithPadding
        get() = (textView.width - (textView.paddingStart + textView.paddingEnd))

    private val startX: Int
        get() {
            val isRtl = textView.layoutDirection == LayoutDirection.RTL
            val lineRight = textView.layout.getLineRight(0).toInt()
            return if (isRtl)
                lineRight - textViewWidthWithPadding
            else
                0
        }

    fun startAnimation(): Completable {
        log("startAnimation")
        with(textView) {
            scroller.startScroll(startX, 0, 0, 0, 0)
            invalidate()

            postDelayed({
                val direction = textView.layout.getParagraphDirection(0)

                scroller.startScroll(
                    startX,
                    0,
                    (textWidth - textViewWidthWithPadding) * direction,
                    0,
                    durationMilliseconds
                )
                invalidate()
            }, delayMilliseconds)
        }

        return scroller.toCompletable()
    }
}

fun TextView.startMarqueeAnimation(
    delayMilliseconds: Long = 0L,
    durationMilliseconds: Int = TimeUnit.SECONDS.toMillis(1).toInt()
): Completable {
    return MarqueeAnimation(this, delayMilliseconds, durationMilliseconds)
        .startAnimation()
}