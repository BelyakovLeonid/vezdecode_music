package com.belyakov.vezdecodemusic.presentation.redact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.belyakov.vezdecodemusic.data.logic.MediaPlayer
import com.belyakov.vezdecodemusic.data.models.AudioInfo
import com.belyakov.vezdecodemusic.data.models.Timecode
import com.belyakov.vezdecodemusic.data.repositories.media.MusicRepository
import com.belyakov.vezdecodemusic.data.repositories.podcast.PodcastRepository
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.koin.core.KoinComponent
import org.koin.core.inject

class RedactViewModel : ViewModel(), KoinComponent {

    private val podcastRepository: PodcastRepository by inject()
    private val musicRepository: MusicRepository by inject()
    private val mediaPlayer: MediaPlayer by inject()

    val audioInfo: LiveData<AudioInfo?> = mediaPlayer.audioInfo
        .distinctUntilChanged()
        .asLiveData()

    val selectedMusicName: LiveData<String?> = musicRepository.selectedAudio
        .map { it?.name }
        .asLiveData()

    init {
        podcastRepository.getPodcast().audioUri?.let {
            mediaPlayer.setFileUri(it)
        }

    }

    fun setTimecodes(timecodes: List<Timecode>) {
        podcastRepository.setPodcastTimecodes(timecodes)
    }

    fun onPlayClicked() {
        mediaPlayer.play()
    }

    fun onPauseClicked() {
        mediaPlayer.pause()
    }

    override fun onCleared() {
        mediaPlayer.stop()
    }

    fun onLeaveScreen() {
        mediaPlayer.stop()
    }
}