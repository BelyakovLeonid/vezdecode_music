package com.belyakov.vezdecodemusic.presentation.finish

import androidx.lifecycle.ViewModel
import com.belyakov.vezdecodemusic.data.repositories.media.MusicRepository
import com.belyakov.vezdecodemusic.data.repositories.podcast.PodcastRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class FinishViewModel : ViewModel(), KoinComponent {

    private val podcastRepository: PodcastRepository by inject()
    private val musicRepository: MusicRepository by inject()

    private var name: String? = null
    private var description: String? = null

    init {
        val podcast = podcastRepository.getPodcast()
        name = podcast.name
        description = podcast.description
    }

    fun getTextForShare(): String {
        return "$name\n$description"
    }

    override fun onCleared() {
        super.onCleared()
        podcastRepository.clearData()
        musicRepository.clearData()
    }
}