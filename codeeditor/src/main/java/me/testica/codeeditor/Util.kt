package me.testica.codeeditor

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan


/**
 * Extension to get metric density pixel
 */
fun Int.toDp(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

/**
 * Return TextWatcher object with listener on afterTextChanged
 * @param predicate void
 */
fun afterTextChanged(predicate: (() -> Unit)): TextWatcher {

   return object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) { predicate.invoke() }
   }
}

/**
 * Extension to remove all ForegroundColorSpan from editable
 */
fun Editable.removeAllSpans() {
    val spans = this.getSpans(0, this.length, ForegroundColorSpan::class.java)
    for (span in spans) this.removeSpan(span)
}
