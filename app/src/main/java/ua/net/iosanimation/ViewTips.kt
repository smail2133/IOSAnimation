package ua.net.iosanimation

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