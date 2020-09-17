package com.belyakov.vezdecodemusic.data.repositories.podcast

import android.net.Uri
import com.belyakov.vezdecodemusic.data.models.Podcast
import com.belyakov.vezdecodemusic.data.models.Timecode

interface PodcastRepository {
    fun getPodcast(): Podcast

    fun setPodcastName(name: String?)
    fun setPodcastDescription(description: String?)
    fun setPodcastImage(imageUri: Uri?)
    fun setPodcastAudio(audioUri: Uri?)
    fun setPodcastTimecodes(timecodes: List<Timecode>)
    fun setPodcastTagContent(hasBadContent: Boolean)
    fun setPodcastTagExport(excludeExport: Boolean)
    fun setPodcastTagTrailer(isTrailer: Boolean)
    fun clearData()
}