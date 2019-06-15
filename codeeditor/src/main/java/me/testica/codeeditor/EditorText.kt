package me.testica.codeeditor

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatEditText
import android.text.InputType
import android.text.Spanned
import android.text.method.ScrollingMovementMethod
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.Gravity
import java.util.regex.Pattern


/**
 * EditText extended to provide:
 * -> Vertical and Horizontal Scrolling
 * -> Listener when line count changes
 * -> Listener when vertical scroll changes
 */
class EditorText(context: Context, attrs: AttributeSet): AppCompatEditText(context, attrs) {

    private var mNumberLines = 0

    private var changeLineCountListener: ((Int) -> Unit)? = null
    private var changeVerticalScrollListener: ((Int) -> Unit)? = null

    private var syntaxHighlightRules: Array<SyntaxHighlightRule>? = emptyArray()

    init {

        // Remove default padding
        includeFontPadding = false
        setPadding(0,0,0,0)

        // Moving content to TOP
        gravity = Gravity.TOP

        // Allow horizontally scrolling
        setHorizontallyScrolling(true)
        movementMethod = ScrollingMovementMethod()

        // Make text selectable
        setTextIsSelectable(true)

        // Hide keyboard suggestions
        inputType = inputType or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

        // "Remove" bottom line
        // TODO: getColor is deprecated
        setBackgroundColor(resources.getColor(android.R.color.transparent))

        addTextChangedListener(afterTextChanged { applySyntaxHighlight() })
    }

    /**
     * Listener that emit the count of lines when changes
     * @param listener function that receives an Int representing the count of line
     */
    fun onChangeLineCount(listener: (Int) -> Unit) {
        changeLineCountListener = listener
    }

    /**
     * Listener that emit the position of scroll Y when changes
     * @param listener function that receives an Int representing the position of scroll Y
     */
    fun onChangeScroll(listener: (scrollY: Int) -> Unit) {
        changeVerticalScrollListener = listener
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

        // Only when the line count differs
        if (mNumberLines != lineCount) {
            mNumberLines = lineCount

            // notify to listener if exists
            changeLineCountListener?.invoke(lineCount)
        }
    }

    override fun onScrollChanged(horiz: Int, vert: Int, oldHoriz: Int, oldVert: Int) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert)

        // Only when vertical scroll differs
        if (vert != oldVert) changeVerticalScrollListener?.invoke(scrollY)
    }

    fun setSyntaxHighlightRules(vararg rules: SyntaxHighlightRule) {
        syntaxHighlightRules = arrayOf(*rules)
    }

    /**
     * Method to highlight syntax according to rules
     * TODO: For now, we are processing whole text, but should be only viewport text
     */
    private fun applySyntaxHighlight() {
        if (syntaxHighlightRules.isNullOrEmpty()) return

        // first remove all spans
        text?.removeAllSpans()

        // set span for proper matching according to a rule
        for (syntaxHighlightRule in syntaxHighlightRules!!) {
            // TODO: catch exception
            val color = Color.parseColor(syntaxHighlightRule.color)
            // TODO: catch exception
            val matcher = Pattern.compile(syntaxHighlightRule.regex).matcher(text)

            while (matcher.find()) text?.setSpan(
                ForegroundColorSpan(color),
                matcher.start(),
                matcher.end(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}