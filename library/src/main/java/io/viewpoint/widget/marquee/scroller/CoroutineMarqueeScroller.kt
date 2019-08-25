package io.viewpoint.widget.marquee.scroller

import android.content.Context
import kotlinx.coroutines.CompletableDeferred

class CoroutineMarqueeScroller(
    context: Context
) : MarqueeScroller(context) {
    private val deferred = CompletableDeferred<Unit>()

    init {
        onComplete = {
            deferred.complete(Unit)
        }
    }

    fun await(): CompletableDeferred<Unit> {
        return deferred
    }
}