package com.belyakov.vezdecodemusic.data.repositories.media

import com.belyakov.vezdecodemusic.data.models.MusicFile
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    val selectedAudio: Flow<MusicFile?>
    fun setSelectedMusic(music: MusicFile)
    fun getAllMedia(): List<MusicFile>
    fun clearData()
}