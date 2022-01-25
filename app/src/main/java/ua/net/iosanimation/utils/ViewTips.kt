package ua.net.iosanimation.utils

import android.view.HapticFeedbackConstants
import android.view.View
import kotlinx.coroutines.*


fun <T> CoroutineScope.debounceClick(
    delayMillis: Long = 1000L,
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (debounceJob == null) {
            debounceJob = this.launch {
                action(param)
                delay(delayMillis)
                debounceJob = null
            }
        }
    }
}

fun View.setDebounceListener(onClickListener: View.OnClickListener) {
    val clickWithDebounce: (view: View) -> Unit =
        MainScope().debounceClick {
            onClickListener.onClick(it)
        }
    this.setOnClickListener(clickWithDebounce)
}

fun View.playHapticFeedback() {
    this.performHapticFeedback(
        HapticFeedbackConstants.VIRTUAL_KEY,
        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
    )
}