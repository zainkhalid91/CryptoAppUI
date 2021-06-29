package com.wallet.yukoni.animations

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator

object ExpandAnim {
    fun expand(v: View?, duration: Int, targetHeight: Int) {
        val prevHeight = v?.height
        v?.visibility = View.VISIBLE
        val valueAnimator = prevHeight?.let { ValueAnimator.ofInt(it, targetHeight) }
        valueAnimator?.addUpdateListener { animation: ValueAnimator? ->
            v.layoutParams.height = animation?.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator?.interpolator = DecelerateInterpolator()
        valueAnimator?.duration = duration.toLong()
        valueAnimator?.start()
    }

    fun collapse(v: View?, duration: Int, targetHeight: Int) {
        val prevHeight = v?.height
        val valueAnimator = prevHeight?.let { ValueAnimator.ofInt(it, targetHeight) }
        valueAnimator?.interpolator = DecelerateInterpolator()
        valueAnimator?.addUpdateListener { animation: ValueAnimator? ->
            v.layoutParams?.height = animation?.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator?.interpolator = DecelerateInterpolator()
        valueAnimator?.duration = duration.toLong()
        valueAnimator?.start()
    }
}