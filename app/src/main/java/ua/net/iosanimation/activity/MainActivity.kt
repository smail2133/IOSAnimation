package ua.net.iosanimation.activity

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ua.net.iosanimation.R
import ua.net.iosanimation.databinding.ActivityMainBinding
import ua.net.iosanimation.utils.*


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
                bnd.backgroundBlur.setImageResource(R.drawable.background_ios)
                bnd.backgroundBlur.setRenderEffect(blurEffect)
            } else {
                vm.blurBitmapWithRenderScript(
                    image = R.drawable.background_ios,
                    context = this@MainActivity
                )
                    .observe(this@MainActivity) {
                        bnd.backgroundBlur.setImageBitmap(it)
                    }
            }
        }

        setContentView(bnd.root)

        bnd.menu.setOnLongClickListener {
            it.playHapticFeedback()
            toggleMenu()
            true
        }
        bnd.menu.setDebounceListener {
            toggleMenu()
        }

        bnd.button1.setOnClickListener(toastClick)
        bnd.button2.setOnClickListener(toastClick)
        bnd.button3.setOnClickListener(toastClick)
        bnd.button4.setOnClickListener(toastClick)
        bnd.button5.setOnClickListener(toastClick)
        bnd.button6.setOnClickListener(toastClick)
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
        if (bnd.button5.visibility == View.VISIBLE) {
            collapseMenu()
        } else {
            super.onBackPressed()
        }
    }

    private fun toggleMenu() {
        if (bnd.button5.visibility == View.VISIBLE) {
            collapseMenu()
        } else {
            expandMenu()
        }
    }

    private fun expandMenu() {
        bnd.menu.playHapticFeedback()

        bnd.menu.iosPulse {
            //change view scale to begin while it invisible.
            bnd.menu.scaleX = 1f
            bnd.menu.scaleY = 1f

            expandCollapseSingleContainerAnimation(container = bnd.menu, isExpand = true)
            visibilityChangeAnimation(
                container = bnd.menu,
                views = arrayOf(bnd.button5, bnd.button6),
                visibility = View.VISIBLE
            )
        }
    }

    private fun collapseMenu() {
        bnd.menu.playHapticFeedback()

        expandCollapseSingleContainerAnimation(container = bnd.menu, isExpand = false)
        visibilityChangeAnimation(
            container = bnd.menu,
            views = arrayOf(bnd.button5, bnd.button6),
            visibility = View.GONE
        )
    }
}