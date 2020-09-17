package com.belyakov.vezdecodemusic.presentation.redact.views

import android.content.Context
import android.content.res.ColorStateList
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.widget.Checkable
import androidx.core.content.ContextCompat
import com.belyakov.vezdecodemusic.R
import kotlinx.android.parcel.Parcelize


class ToggleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr), Checkable {

    var onCheckedListener: ((Boolean) -> Unit)? = null
    private val colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_enabled)
        ),
        intArrayOf(
            ContextCompat.getColor(context, R.color.colorWhite),
            ContextCompat.getColor(context, R.color.colorBlue),
            ContextCompat.getColor(context, R.color.colorBlueLight),
        )
    )

    private var checkedState = false

    init {
        isClickable = true
        scaleType = ScaleType.CENTER
        setBackgroundResource(R.drawable.d_toggle_background)
        imageTintList = colorStateList
    }

    private val checkedStateSet = intArrayOf(
        android.R.attr.state_checked
    )

    override fun onCreateDrawableState(extraSpace: Int): IntArray? {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (checkedState) {
            mergeDrawableStates(drawableState, checkedStateSet)
        }
        return drawableState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(null)
        checkedState = (state as SavedState).checkedState
        refreshDrawableState()
    }

    override fun onSaveInstanceState(): Parcelable? {
        super.onSaveInstanceState()
        return SavedState(checkedState)
    }

    @Parcelize
    private data class SavedState(
        val checkedState: Boolean
    ) : Parcelable

    override fun isChecked(): Boolean = checkedState

    override fun setChecked(checked: Boolean) {
        checkedState = checked
        onCheckedListener?.invoke(checked)
    }

    override fun toggle() {
        checkedState = !checkedState
        onCheckedListener?.invoke(checkedState)
    }

    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }
}