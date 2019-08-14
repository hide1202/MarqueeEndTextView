package io.viewpoint.widget.marquee

import android.content.res.Resources
import android.util.TypedValue

val Int.toDp: Int
    get() = Math.round(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(), Resources.getSystem().displayMetrics
        )
    )