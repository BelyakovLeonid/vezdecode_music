package com.belyakov.vezdecodemusic.data.repositories.media

import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.data.models.MusicFile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
class MusicRepositoryMocked : MusicRepository {

    private val mutableSelectedAudio = MutableStateFlow<MusicFile?>(null)
    override val selectedAudio: Flow<MusicFile?>
        get() = mutableSelectedAudio

    override fun setSelectedMusic(music: MusicFile) {
        mutableSelectedAudio.value = music
    }

    override fun getAllMedia(): List<MusicFile> {
        return listOf(
            MusicFile("BTSTU", "Jai Paul", R.drawable.ic_music_1, 210_000),
            MusicFile("All of the Lights", "Kanye West", R.drawable.ic_music_2, 310_000),
            MusicFile("stevie", "Kasabian", R.drawable.ic_music_3, 235_000),
            MusicFile("Lights Up", "Harry Styles", R.drawable.ic_music_4, 440_000),
            MusicFile("Idontknow", "Jamie xx", R.drawable.ic_music_5, 330_000),
            MusicFile("Let It Happen", "Tame Impala", R.drawable.ic_music_6, 110_000),
            MusicFile("Assume Form", "James Blake", R.drawable.ic_music_9, 255_000),
            MusicFile("Alt-J", "BTSTU", R.drawable.ic_music_2, 466_000),
            MusicFile("Tame Impala", "Alt-J", R.drawable.ic_music_5, 321_000),
            MusicFile("Yuna, Little Simz", "Tame Impala", R.drawable.ic_music_1, 60_000),
            MusicFile("Kasabian", "All of the Lights", R.drawable.ic_music_3, 55_000),
        )
    }

    override fun clearData() {
        mutableSelectedAudio.value = null
    }
}