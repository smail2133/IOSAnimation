package ua.net.iosanimation.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import ua.net.iosanimation.R

fun View.iosPulse(whenEnd: () -> Unit) {
    val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat("scaleX", 0.9f),
        PropertyValuesHolder.ofFloat("scaleY", 0.9f)
    )
    scaleDown.duration = resources.getInteger(R.integer.transform_animation_duration).toLong()
    scaleDown.repeatCount = 0
    scaleDown.repeatMode = ObjectAnimator.REVERSE
    scaleDown.interpolator = FastOutSlowInInterpolator()

    val animationListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator?) {

        }

        override fun onAnimationEnd(p0: Animator?) {
            scaleDown.removeListener(this)
            whenEnd.invoke()
        }

        override fun onAnimationCancel(p0: Animator?) {

        }

        override fun onAnimationRepeat(p0: Animator?) {

        }
    }

    scaleDown.addListener(animationListener)
    scaleDown.start()
}

fun View.iosPulseBack(whenEnd: () -> Unit) {
    val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat("scaleX", 1f),
        PropertyValuesHolder.ofFloat("scaleY", 1f)
    )
    scaleDown.duration = resources.getInteger(R.integer.transform_animation_duration).toLong()
    scaleDown.repeatCount = 0
    scaleDown.repeatMode = ObjectAnimator.REVERSE
    scaleDown.interpolator = FastOutSlowInInterpolator()

    val animationListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator?) {

        }

        override fun onAnimationEnd(p0: Animator?) {
            scaleDown.removeListener(this)
            whenEnd.invoke()
        }

        override fun onAnimationCancel(p0: Animator?) {

        }

        override fun onAnimationRepeat(p0: Animator?) {

        }
    }

    scaleDown.addListener(animationListener)
    scaleDown.start()
}

fun expandCollapseAnimation(fromV: View, toV: View, container: ViewGroup, isExpand: Boolean) {
    val transform = MaterialContainerTransform().apply {
        startView = if (isExpand) fromV else toV
        endView = if (isExpand) toV else fromV
        scrimColor = Color.TRANSPARENT
        endElevation = if (isExpand) 4.dp.toFloat() else 0.dp.toFloat()
        startElevation = if (isExpand) 0.dp.toFloat() else 4.dp.toFloat()
        duration = container.resources.getInteger(R.integer.transform_animation_duration).toLong()
        interpolator = FastOutSlowInInterpolator()
        setPathMotion(MaterialArcMotion())
        fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        isHoldAtEndEnabled = false

        addTarget(if (isExpand) toV else fromV)
    }

    if (isExpand) {
        fromV.visibility = View.INVISIBLE
        toV.visibility = View.VISIBLE
    } else {
        toV.visibility = View.INVISIBLE
        fromV.visibility = View.VISIBLE
    }

    TransitionManager.beginDelayedTransition(container, transform)
}

fun expandCollapseSingleContainerAnimation(container: ViewGroup, isExpand: Boolean) {
    val heightAnimator =
        ValueAnimator.ofInt(
            container.measuredHeight,
            if (isExpand) container.measuredHeight * 2 else container.measuredHeight / 2
        )
    heightAnimator.duration =
        container.resources.getInteger(R.integer.transform_animation_duration).toLong()
    heightAnimator.addUpdateListener {
        val animatedValue = heightAnimator.animatedValue as Int
        val layoutParams = container.layoutParams
        layoutParams.height = animatedValue

        container.requestLayout()
    }

    val widthAnimator =
        ValueAnimator.ofInt(
            container.measuredWidth,
            if (isExpand) container.measuredWidth * 2 else container.measuredWidth / 2
        )
    widthAnimator.duration =
        container.resources.getInteger(R.integer.transform_animation_duration).toLong()
    widthAnimator.addUpdateListener {
        val animatedValue = widthAnimator.animatedValue as Int
        val layoutParams = container.layoutParams
        layoutParams.width = animatedValue

        container.requestLayout()
    }

    heightAnimator.start()
    widthAnimator.start()
}

fun visibilityChangeAnimation(container: ViewGroup, vararg views: View, visibility: Int) {
    val transition: Transition = Fade()

    transition.apply {
        interpolator = FastOutSlowInInterpolator()
        duration = container.resources.getInteger(R.integer.transform_animation_duration).toLong()
    }

    views.map {
        transition.addTarget(container)
    }

    TransitionManager.beginDelayedTransition(container, transition)

    views.map {
        it.visibility = visibility
    }
}