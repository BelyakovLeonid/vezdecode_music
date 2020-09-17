package com.belyakov.vezdecodemusic.presentation.redact.views

import android.content.Context
import android.util.AttributeSet
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.utils.getStyleAttrs

class PlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    var onPlayListener: (() -> Unit)? = null
    var onPauseListener: (() -> Unit)? = null

    private var playIconRes: Int = 0
    private var pauseIconRes: Int = 0
    private var isPlaying: Boolean = false
        set(value) {
            field = value
            handleState()
        }

    init {
        getStyleAttrs(attrs, R.styleable.PlayView, defStyleAttr) {
            playIconRes = it.getResourceId(R.styleable.PlayView_play_icon, 0)
            pauseIconRes = it.getResourceId(R.styleable.PlayView_pause_icon, 0)
        }
        isPlaying = false
        setOnClickListener {
            if (isPlaying) {
                onPauseListener?.invoke()
            } else {
                onPlayListener?.invoke()
            }
            isPlaying = !isPlaying
        }
    }

    private fun handleState() {
        setImageResource(if(isPlaying) pauseIconRes else playIconRes)
    }
}