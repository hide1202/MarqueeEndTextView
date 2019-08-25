package io.viewpoint.widget.marquee.scroller

import android.content.Context
import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject

class CompletableMarqueeScroller(
    context: Context
) : MarqueeScroller(context) {
    private val completeSignal = CompletableSubject.create()

    init {
        onComplete = {
            completeSignal.onComplete()
        }
    }

    fun toCompletable(): Completable = completeSignal.hide()
}