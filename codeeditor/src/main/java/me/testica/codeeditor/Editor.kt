package me.testica.codeeditor

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import java.lang.Exception


class Editor(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val editText = EditorText(context, attrs)
    private val numLinesView = EditorNumberLines(context, attrs)

    private var mText = ""
    private var mTextSize: Float = 12F
    private var mTypeface: Typeface = Typeface.DEFAULT

    init {

        // add number lines
        addNumLinesView()

        // add edittext
        addEditText()

        // Get and set custom attributes
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.Editor, 0, 0)
        try {
            // load text size
            setTextSize(a.getDimension(R.styleable.Editor_textSize, mTextSize))

            // load typeface
            try {
                setTypeface(a.getResourceId(R.styleable.Editor_fontFamily, -1).let {
                    if (it == -1) mTypeface else ResourcesCompat.getFont(context, it)!!
                })
            } catch (e: Exception) {
                Log.w("Typeface", e.localizedMessage)
            }
        }
        finally {
            a.recycle()
        }

        // default attrs
        orientation = HORIZONTAL

        // Observe changes in edittext line count
        editText.onChangeLineCount { lineCount ->
            var lines = ""
            for (i in 1..lineCount) lines += "$i\n"
            numLinesView.text = lines
        }

        // Observe changes in edittext scroll
        editText.onChangeScroll { scrollY ->
            numLinesView.scrollY = scrollY
        }

        // Deliver touch events on numlines to edittext
        numLinesView.setOnTouchListener { _, event ->
            editText.onTouchEvent(event)
        }
    }

    private fun addEditText() {
        addView(editText)
    }

    private fun addNumLinesView() {
        addView(numLinesView)
    }

    /**
     * Get edit text widget to access the whole API
     * Make whatever you want, but keep in mind that you may change default behaviour.
     * Get Fun!
     */
    fun getEditText(): EditorText {
        return editText
    }

    /**
     * Get number lines widget to access the whole API
     * Make whatever you want, but keep in mind that you may change default behaviour.
     * Get Fun!
     */
    fun getNumLinesView(): EditorNumberLines {
        return numLinesView
    }

    /**
     * Set syntax highlight rules
     * Keep in mind that rules may overlapped highlight of others, so the rule position matter
     * The last rules will overlap the highlight of first ones
     * @param rules set of rules
     */
    fun setSyntaxHighlightRules(vararg rules: SyntaxHighlightRule) {
        editText.setSyntaxHighlightRules(*rules)
    }

    /**
     * Set text to edit text
     * @param text string to set
     */
    fun setText(text: String) {
        mText = text

        editText.setText(text)
    }

    /**
     * Get text from edit text
     */
    fun getText(): String {
        mText = getEditText().text.toString()
        return mText
    }

    /**
     * Set Typeface to edit text and number lines
     * @param typeface
     */
    fun setTypeface(typeface: Typeface) {
        mTypeface = typeface

        editText.typeface = typeface
        numLinesView.typeface = typeface
    }

    /**
     * Get Typeface of edit text and number lines
     */
    fun getTypeface() : Typeface {
        return mTypeface
    }

    /**
     * Set text size to edit text and number lines
     * @param size size of font as float
     */
    fun setTextSize(size: Float) {
        mTextSize = size

        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        numLinesView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    /**
     * Get text size from edit text and number liunes
     */
    fun getTextSize(): Float {
        return mTextSize
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        super.setLayoutParams(params)

        params?.let {
            // set layout to edittext
            editText.layoutParams = LinearLayout.LayoutParams(0, params.height).apply {
                weight = 1f
            }

            // set layout yo numlinesview
            numLinesView.layoutParams = numLinesView.layoutParams.apply {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                height = params.height
            }

        }
    }
}