package com.belyakov.vezdecodemusic.di

import com.belyakov.vezdecodemusic.data.logic.MediaPlayer
import com.belyakov.vezdecodemusic.data.logic.MediaPlayerImpl
import com.belyakov.vezdecodemusic.data.repositories.media.MusicRepository
import com.belyakov.vezdecodemusic.data.repositories.media.MusicRepositoryMocked
import com.belyakov.vezdecodemusic.data.repositories.podcast.PodcastRepository
import com.belyakov.vezdecodemusic.data.repositories.podcast.PodcastRepositoryImpl
import com.belyakov.vezdecodemusic.presentation.endsetup.EndSetupFragment
import com.belyakov.vezdecodemusic.presentation.endsetup.EndSetupViewModel
import com.belyakov.vezdecodemusic.presentation.finish.FinishFragment
import com.belyakov.vezdecodemusic.presentation.finish.FinishViewModel
import com.belyakov.vezdecodemusic.presentation.music.ChooseMusicFragment
import com.belyakov.vezdecodemusic.presentation.music.ChooseMusicViewModel
import com.belyakov.vezdecodemusic.presentation.redact.RedactFragment
import com.belyakov.vezdecodemusic.presentation.redact.RedactViewModel
import com.belyakov.vezdecodemusic.presentation.setup.SetupFragment
import com.belyakov.vezdecodemusic.presentation.setup.SetupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    scope(named<SetupFragment>()) { viewModel { SetupViewModel() } }
    scope(named<EndSetupFragment>()) { viewModel { EndSetupViewModel() } }
    scope(named<RedactFragment>()) { viewModel { RedactViewModel() } }
    scope(named<ChooseMusicFragment>()) { viewModel { ChooseMusicViewModel() } }
    scope(named<FinishFragment>()) { viewModel { FinishViewModel() } }
}

val repositoriesModule = module {
    single<PodcastRepository> { PodcastRepositoryImpl() }
    single<MusicRepository> { MusicRepositoryMocked() }
}

val logicModule = module {
    single<MediaPlayer> { MediaPlayerImpl() }
}
