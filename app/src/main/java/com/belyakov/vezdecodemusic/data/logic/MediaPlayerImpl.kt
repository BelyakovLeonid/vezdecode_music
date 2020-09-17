package com.belyakov.vezdecodemusic.data.logic

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.net.Uri
import android.util.Log
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.data.models.AudioInfo
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.KoinComponent
import org.koin.core.inject

@ExperimentalCoroutinesApi
class MediaPlayerImpl : MediaPlayer, KoinComponent, AudioManager.OnAudioFocusChangeListener {

    private var player: ExoPlayer? = null
    private val context: Context by inject()
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var focusRequest: AudioFocusRequest = setupAudioFocusRequest()

    private val mutableAudioInfo = MutableStateFlow<AudioInfo?>(null)

    override val audioInfo: Flow<AudioInfo?>
        get() = mutableAudioInfo

    private fun setupAudioFocusRequest(): AudioFocusRequest {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()

        return AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(audioAttributes)
            .setOnAudioFocusChangeListener(this)
            .build()
    }

    override fun setFileUri(uri: Uri) {
        player = SimpleExoPlayer.Builder(context).build().also {
            it.playWhenReady = false
            it.seekTo(0, 0)

            val mediaFactory = DefaultDataSourceFactory(context, "nestor-exo-player")
            val mediaSource = ProgressiveMediaSource.Factory(mediaFactory)
                .createMediaSource(uri)

            it.addListener(object : Player.EventListener {
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    super.onPlayerStateChanged(playWhenReady, playbackState)
                    if (playbackState == ExoPlayer.STATE_READY) {
                        mutableAudioInfo.value = AudioInfo(
                            uri.path ?: context.getString(R.string.unknown_file),
                            it.duration
                        )
                    }
                }
            })

            it.prepare(mediaSource, false, false)
        }
    }

    override fun play() {
        requestAudioFocus {
            player?.playWhenReady = true
        }
    }

    override fun pause() {
        player?.playWhenReady = false
    }

    override fun stop() {
        player?.playWhenReady = false
        player?.seekTo(0)
    }

    override fun release() {
        releaseAudioFocus()
        player?.release()
        player = null
    }

    private fun requestAudioFocus(onFocusGrantedListener: () -> Unit) {
        releaseAudioFocus()
        if (audioManager.requestAudioFocus(focusRequest) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            onFocusGrantedListener.invoke()
        }
    }

    private fun releaseAudioFocus() {
        audioManager.abandonAudioFocusRequest(focusRequest)
    }


    override fun onAudioFocusChange(focusChange: Int) {
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> play()
            AudioManager.AUDIOFOCUS_LOSS -> pause()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> pause()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> pause()
        }
    }
}