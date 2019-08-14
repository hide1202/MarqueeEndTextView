package io.viewpoint.widget.marquee

import android.widget.TextView

fun TextView.toMarqueeAnimation(): MarqueeAnimation = MarqueeAnimation(this)