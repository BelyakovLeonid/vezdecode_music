package com.belyakov.vezdecodemusic.data.repositories.podcast

import android.net.Uri
import com.belyakov.vezdecodemusic.data.models.Podcast
import com.belyakov.vezdecodemusic.data.models.Timecode

class PodcastRepositoryImpl: PodcastRepository {

    private var podcast = Podcast()

    override fun getPodcast() = podcast

    override fun setPodcastName(name: String?) {
        podcast.name = name
    }

    override fun setPodcastDescription(description: String?) {
        podcast.description = description
    }

    override fun setPodcastImage(imageUri: Uri?) {
        podcast.imageUri = imageUri
    }

    override fun setPodcastAudio(audioUri: Uri?) {
        podcast.audioUri = audioUri
    }

    override fun setPodcastTimecodes(timecodes: List<Timecode>) {
        podcast.timecodes = timecodes
    }

    override fun setPodcastTagContent(hasBadContent: Boolean) {
        podcast.tagContent = hasBadContent
    }

    override fun setPodcastTagExport(excludeExport: Boolean) {
        podcast.tagExport = excludeExport
    }

    override fun setPodcastTagTrailer(isTrailer: Boolean) {
        podcast.tagTrailer = isTrailer
    }

    override fun clearData() {
        podcast = Podcast()
    }
}