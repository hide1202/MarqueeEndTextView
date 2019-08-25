package io.viewpoint.widget.marquee

import android.graphics.Rect
import android.util.LayoutDirection
import android.util.Log
import android.widget.TextView
import io.reactivex.Completable
import io.viewpoint.widget.marquee.scroller.CompletableMarqueeScroller
import io.viewpoint.widget.marquee.scroller.CoroutineMarqueeScroller
import io.viewpoint.widget.marquee.scroller.DefaultMarqueeScroller
import io.viewpoint.widget.marquee.scroller.MarqueeScroller
import kotlinx.coroutines.Deferred
import java.util.concurrent.TimeUnit

fun log(msg: String) {
    Log.d("Marquee", msg)
}

class MarqueeAnimation(
    private val textView: TextView,
    private val delayMilliseconds: Long,
    private val durationMilliseconds: Long,
    private val marqueeScroller: MarqueeScroller
) {
    init {
        textView.setScroller(marqueeScroller)
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

    fun startAnimation() {
        log("startAnimation")
        with(textView) {
            marqueeScroller.startScroll(startX, 0, 0, 0, 0)
            invalidate()

            postDelayed({
                val direction = textView.layout.getParagraphDirection(0)

                marqueeScroller.startScroll(
                    startX,
                    0,
                    (textWidth - textViewWidthWithPadding) * direction,
                    0,
                    durationMilliseconds.toInt()
                )
                invalidate()
            }, delayMilliseconds)
        }
    }
}

fun TextView.observeMarqueeAnimation(
    delayMilliseconds: Long = 0L,
    durationMilliseconds: Long = TimeUnit.SECONDS.toMillis(1)
): Completable {
    val scroller = CompletableMarqueeScroller(context)
    return scroller.toCompletable().also {
        MarqueeAnimation(
            this,
            delayMilliseconds,
            durationMilliseconds,
            scroller
        ).startAnimation()
    }
}

fun TextView.startMarqueeAnimationAsync(
    delayMilliseconds: Long = 0L,
    durationMilliseconds: Long = TimeUnit.SECONDS.toMillis(1),
    onComplete: (() -> Unit)
) {
    val scroller = DefaultMarqueeScroller(context, onComplete)
    MarqueeAnimation(
        this,
        delayMilliseconds,
        durationMilliseconds,
        scroller
    ).startAnimation()
}

fun TextView.awaitMarqueeAnimation(
    delayMilliseconds: Long = 0L,
    durationMilliseconds: Long = TimeUnit.SECONDS.toMillis(1)
): Deferred<Unit> {
    val scroller = CoroutineMarqueeScroller(context)
    MarqueeAnimation(
        this,
        delayMilliseconds,
        durationMilliseconds,
        scroller
    ).startAnimation()
    return scroller.await()
}