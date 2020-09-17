package com.belyakov.vezdecodemusic.presentation.endsetup

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belyakov.vezdecodemusic.data.logic.MediaPlayer
import com.belyakov.vezdecodemusic.data.models.Timecode
import com.belyakov.vezdecodemusic.data.repositories.podcast.PodcastRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class EndSetupViewModel: ViewModel(), KoinComponent {
    private val podcastRepository: PodcastRepository by inject()
    private val mediaPlayer: MediaPlayer by inject()

    private val _podcastName = MutableLiveData<String?>()
    val podcastName: LiveData<String?> = _podcastName

    private val _podcastDescription = MutableLiveData<String?>()
    val podcastDescription: LiveData<String?> = _podcastDescription

    private val _podcastImage = MutableLiveData<Uri?>()
    val podcastImage: LiveData<Uri?> = _podcastImage

    private val _podcastTimecodes = MutableLiveData<List<Timecode>>()
    val podcastTimecodes: LiveData<List<Timecode>> = _podcastTimecodes

    private val podcastAudio = MutableLiveData<Uri?>()

    init {
        val podcast = podcastRepository.getPodcast()
        _podcastName.value = podcast.name
        _podcastDescription.value = podcast.description
        _podcastImage.value = podcast.imageUri
        podcastAudio.value = podcast.audioUri
        _podcastTimecodes.value = podcast.timecodes
    }

    fun onPlayClicked(){
        mediaPlayer.play()
    }

    fun onPauseClicked(){
        mediaPlayer.pause()
    }
}