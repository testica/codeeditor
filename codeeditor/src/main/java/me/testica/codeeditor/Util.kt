package me.testica.codeeditor

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import java.util.*


/**
 * Extension to get metric density pixel
 */
fun Int.toDp(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

/**
 * Extension TextChangedListener but with delay (1 second)
 * @param predicate editable emitted after text changed
 */
fun EditorText.delayedTextChanged(predicate: ((Editable?) -> Unit)) {
    val ms: Long = 1000
    var timer : Timer? = null

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            if (timer != null) timer!!.cancel()
            timer = Timer()
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    predicate.invoke(s)
                }
            }, ms)
        }
    })
}

/**
 * Extension to remove all ForegroundColorSpan from editable
 */
fun Editable.removeAllSpans() {
    val spans = this.getSpans(0, this.length, ForegroundColorSpan::class.java)
    for (span in spans) this.removeSpan(span)
}
