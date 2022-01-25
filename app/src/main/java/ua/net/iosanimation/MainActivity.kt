package ua.net.iosanimation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ua.net.iosanimation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bnd: ActivityMainBinding
    private val vm: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityMainBinding.inflate(LayoutInflater.from(this))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val blurEffect = BlurUtils.renderEffect()
            bnd.backgroundBlur.setImageResource(R.drawable.backgound_ios)
            bnd.backgroundBlur.setRenderEffect(blurEffect)
        } else {
            vm.blurBitmapWithRenderScript(image = R.drawable.backgound_ios, context = this)
                .observe(this, {
                    bnd.backgroundBlur.setImageBitmap(it)
                })
        }

        setContentView(bnd.root)


    }
}