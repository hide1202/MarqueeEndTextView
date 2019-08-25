package io.viewpoint.widget.marquee.scroller

import android.content.Context
import android.view.animation.LinearInterpolator
import android.widget.Scroller
import io.viewpoint.widget.marquee.log

abstract class MarqueeScroller(
    context: Context,
    protected var onComplete: (() -> Unit)? = null
) : Scroller(context, LinearInterpolator()) {
    override fun computeScrollOffset(): Boolean {
        val result = super.computeScrollOffset()
        log("computeScrollOffset : $isFinished, result : $result")
        if (isFinished && duration > 0) {
            onComplete?.invoke()
        }
        return result
    }
}