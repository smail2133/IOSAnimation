package ua.net.iosanimation.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import ua.net.iosanimation.*
import ua.net.iosanimation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bnd: ActivityMainBinding
    private val vm: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityMainBinding.inflate(LayoutInflater.from(this))

        bnd.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val blurEffect = BlurUtils.renderEffect()
                bnd.backgroundBlur.setImageResource(R.drawable.backgound_ios)
                bnd.backgroundBlur.setRenderEffect(blurEffect)
            } else {
                vm.blurBitmapWithRenderScript(
                    image = R.drawable.backgound_ios,
                    context = this@MainActivity
                )
                    .observe(this@MainActivity, {
                        bnd.backgroundBlur.setImageBitmap(it)
                    })
            }
        }

        setContentView(bnd.root)

        bnd.smallMenu.setOnLongClickListener {
            it.playHapticFeedback()
            expandMenu()
            true
        }
        bnd.smallMenu.setDebounceListener {
            expandMenu()
        }
        bnd.extendedMenu.setDebounceListener {
            collapseMenu()
        }

        bnd.button1.setOnClickListener(toastClick)
        bnd.button2.setOnClickListener(toastClick)
        bnd.button3.setOnClickListener(toastClick)
        bnd.button4.setOnClickListener(toastClick)
        bnd.buttonExtended1.setOnClickListener(toastClick)
        bnd.buttonExtended2.setOnClickListener(toastClick)
        bnd.buttonExtended3.setOnClickListener(toastClick)
        bnd.buttonExtended4.setOnClickListener(toastClick)
        bnd.buttonExtended5.setOnClickListener(toastClick)
        bnd.buttonExtended6.setOnClickListener(toastClick)
    }

    private val toastClick = View.OnClickListener { p0 ->
        p0?.playHapticFeedback()

        Toast.makeText(
            this@MainActivity,
            p0?.tag.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onBackPressed() {
        when (bnd.extendedMenu.visibility) {
            View.VISIBLE -> collapseMenu()
            View.INVISIBLE -> super.onBackPressed()
            else -> super.onBackPressed()
        }
    }

    private fun expandMenu() {
        bnd.smallMenu.playHapticFeedback()

        val transform = MaterialContainerTransform().apply {
            startView = bnd.smallMenu
            endView = bnd.extendedMenu
            scrimColor = Color.TRANSPARENT
            endElevation = 4.dp.toFloat()
            startElevation = 0.dp.toFloat()
            duration = resources.getInteger(R.integer.animation_duration).toLong()
            interpolator = FastOutSlowInInterpolator()
            setPathMotion(MaterialArcMotion())
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            isHoldAtEndEnabled = false

            addTarget(bnd.extendedMenu)
        }

        bnd.smallMenu.visibility = View.INVISIBLE
        bnd.extendedMenu.visibility = View.VISIBLE

        TransitionManager.beginDelayedTransition(bnd.root, transform)
    }

    private fun collapseMenu() {
        bnd.extendedMenu.playHapticFeedback()

        val transform = MaterialContainerTransform().apply {
            startView = bnd.extendedMenu
            endView = bnd.smallMenu
            scrimColor = Color.TRANSPARENT
            startElevation = 4.dp.toFloat()
            endElevation = 0.dp.toFloat()
            duration = resources.getInteger(R.integer.animation_duration).toLong()
            interpolator = FastOutSlowInInterpolator()
            setPathMotion(MaterialArcMotion())
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            isHoldAtEndEnabled = false

            addTarget(bnd.smallMenu)
        }

        TransitionManager.beginDelayedTransition(bnd.root, transform)

        bnd.extendedMenu.visibility = View.INVISIBLE
        bnd.smallMenu.visibility = View.VISIBLE
    }
}