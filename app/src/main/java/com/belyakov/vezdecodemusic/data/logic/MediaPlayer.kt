package com.belyakov.vezdecodemusic.data.logic

import android.net.Uri
import com.belyakov.vezdecodemusic.data.models.AudioInfo
import kotlinx.coroutines.flow.Flow

interface MediaPlayer {
    val audioInfo: Flow<AudioInfo?>
    fun setFileUri(uri: Uri)
    fun play()
    fun pause()
    fun stop()
    fun release()
}