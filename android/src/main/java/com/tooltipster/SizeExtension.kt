package com.tooltipster

import android.content.res.Resources
import android.graphics.Point
import android.util.TypedValue
import kotlin.math.roundToInt

internal val Int.dp: Int
  @JvmSynthetic inline get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics
  ).roundToInt()

/** returns float dimensional value from the float px value. */
internal val Float.dp: Float
  @JvmSynthetic inline get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    Resources.getSystem().displayMetrics
  )

/** gets display size as a point. */
internal val displaySize: Point
  @JvmSynthetic inline get() = Point(
    Resources.getSystem().displayMetrics.widthPixels,
    Resources.getSystem().displayMetrics.heightPixels
  )
