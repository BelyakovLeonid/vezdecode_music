package com.belyakov.vezdecodemusic.presentation.setup.views

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.utils.getStyleAttrs
import kotlinx.android.synthetic.main.v_setup_field.view.*

class SetupFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var textChangeListener: ((String?) -> Unit)? = null

    init {
        View.inflate(context, R.layout.v_setup_field, this)
        getStyleAttrs(attrs, R.styleable.SetupFieldView, defStyleAttr) {
            editText.hint = it.getString(R.styleable.SetupFieldView_hint)
            title.text = it.getString(R.styleable.SetupFieldView_title)
            when (it.getInt(R.styleable.SetupFieldView_inputType, 0)) {
                0 -> editText.inputType = InputType.TYPE_CLASS_TEXT
                1 -> {
                    editText.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                    editText.isSingleLine = false
                }
            }
        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                textChangeListener?.invoke(p0.toString())
            }
        })
    }
}