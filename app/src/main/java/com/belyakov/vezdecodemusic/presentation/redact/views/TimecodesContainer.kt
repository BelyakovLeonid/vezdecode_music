package com.belyakov.vezdecodemusic.presentation.redact.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.fragment.app.FragmentManager
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.data.models.Timecode

class TimecodesContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var fragmentManager: FragmentManager? = null

    init {
        orientation = VERTICAL
    }

    fun addTimeCode() {
        addView(getNewChild(), childCount)
    }

    private fun getNewChild() = TimeCodeView(context).apply {
        onCloseListener = {
            this@TimecodesContainer.removeView(it)
        }
        fragmentManager = this@TimecodesContainer.fragmentManager
        setTimecodeName(
            context.getString(
                R.string.timecode_name,
                this@TimecodesContainer.childCount + 1
            )
        )
    }

    fun getTimecodes() = children
        .map { (it as TimeCodeView).getTimecode() }
        .toList()
}