package io.viewpoint.widget.marquee.scroller

import android.content.Context

class DefaultMarqueeScroller(
    context: Context,
    onComplete: () -> Unit
) : MarqueeScroller(context, onComplete)
