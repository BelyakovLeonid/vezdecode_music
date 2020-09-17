package com.belyakov.vezdecodemusic.presentation.music

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belyakov.vezdecodemusic.data.models.MusicFile
import com.belyakov.vezdecodemusic.data.repositories.media.MusicRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class ChooseMusicViewModel : ViewModel(), KoinComponent {
    private val musicRepository: MusicRepository by inject()

    private val _musicDataSet = MutableLiveData<List<MusicFile>>()
    val musicDataSet: LiveData<List<MusicFile>> = _musicDataSet

    init {
        _musicDataSet.value = musicRepository.getAllMedia()
    }

    fun onMusicSelected(music: MusicFile) {
        musicRepository.setSelectedMusic(music)
    }
}
