package ua.net.iosanimation.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
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