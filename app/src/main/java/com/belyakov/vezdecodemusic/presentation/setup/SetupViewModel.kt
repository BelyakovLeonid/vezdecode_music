package com.belyakov.vezdecodemusic.presentation.setup

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.belyakov.vezdecodemusic.data.logic.MediaPlayer
import com.belyakov.vezdecodemusic.data.models.AudioInfo
import com.belyakov.vezdecodemusic.data.repositories.podcast.PodcastRepository
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import org.koin.core.KoinComponent
import org.koin.core.inject

class SetupViewModel : ViewModel(), KoinComponent {

    private val repository: PodcastRepository by inject()
    private val mediaPlayer: MediaPlayer by inject()

    private var name: String? = null
    private var description: String? = null
    private var imageUri: Uri? = null
    private var audioUri: Uri? = null

    private val _setupDone = MutableLiveData<Boolean>()
    val setupDone: LiveData<Boolean> = _setupDone

    val audioInfo: LiveData<AudioInfo?> = mediaPlayer.audioInfo
        .distinctUntilChanged()
        .asLiveData()

    fun setPodcastName(name: String?) {
        repository.setPodcastName(name)
        this.name = name
        updateSetupState()
    }

    fun setPodcastDescription(description: String?) {
        repository.setPodcastDescription(description)
        this.description = description
        updateSetupState()
    }

    fun setPodcastImage(imageUri: Uri) {
        repository.setPodcastImage(imageUri)
        this.imageUri = imageUri
        updateSetupState()
    }

    fun setPodcastAudio(audioUri: Uri) {
        repository.setPodcastAudio(audioUri)
        mediaPlayer.setFileUri(audioUri)
        this.audioUri = audioUri
        updateSetupState()
    }

    fun setPodcastTagContent(hasBadContent: Boolean) {
        repository.setPodcastTagContent(hasBadContent)
    }

    fun setPodcastTagExport(excludeExport: Boolean) {
        repository.setPodcastTagExport(excludeExport)
    }

    fun setPodcastTagTrailer(isTrailer: Boolean) {
        repository.setPodcastTagTrailer(isTrailer)
    }

    private fun updateSetupState() {
        _setupDone.value = name != null
                && description != null
                && audioUri != null
                && imageUri != null
    }
}
