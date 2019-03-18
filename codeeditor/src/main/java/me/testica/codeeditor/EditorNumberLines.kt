package me.testica.codeeditor

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView

/**
 * Vertical TextView that represent number of lines
 */
class EditorNumberLines(context: Context, attrs: AttributeSet): TextView(context, attrs) {

    companion object {
        private const val RIGHT_PADDING = 5
    }

    init {
        // reset attributes
        includeFontPadding = false
        setPadding(0,0, RIGHT_PADDING.toDp(context),0)

        // set vertical scroll
        isVerticalScrollBarEnabled = true
        movementMethod = ScrollingMovementMethod()

        // default number line to "1"
        text = text.toString().plus("1\n")

        // Moving content to TOP and RIGHT
        gravity = Gravity.END or Gravity.END
    }
}