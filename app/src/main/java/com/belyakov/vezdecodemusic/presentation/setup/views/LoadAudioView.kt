package com.belyakov.vezdecodemusic.presentation.setup.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.data.models.AudioInfo
import com.belyakov.vezdecodemusic.utils.toTimeString
import kotlinx.android.synthetic.main.v_load_audio.view.*


class LoadAudioView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onLoadClickedListener: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.v_load_audio, this)
        loadButton.setOnClickListener {
            onLoadClickedListener?.invoke()
        }
        redactButton.setOnClickListener {
            onLoadClickedListener?.invoke()
        }
    }

    fun loadAudioInfo(audioInfo: AudioInfo?) {
        Log.d("MyTag", "audioInfo = $audioInfo")
        podcastName.text = audioInfo?.name
        podcastDuration.text = audioInfo?.duration?.toTimeString()
        loadGroup.isVisible = audioInfo == null
        loadedGroup.isVisible = audioInfo != null
    }
}