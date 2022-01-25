package ua.net.iosanimation.activity

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.net.iosanimation.utils.BlurUtils


class MainActivityViewModel : ViewModel() {

    fun blurBitmapWithRenderScript(@DrawableRes image: Int, context: Context): LiveData<Bitmap> {
        val lv = MutableLiveData<Bitmap>()

        viewModelScope.launch(Dispatchers.IO) {
            val bm = ContextCompat.getDrawable(context, image)?.toBitmap()

            bm?.let {
               val blurBitmap = BlurUtils.blur(context = context, image = it)
                lv.postValue(blurBitmap)
            }
        }

        return lv
    }
}