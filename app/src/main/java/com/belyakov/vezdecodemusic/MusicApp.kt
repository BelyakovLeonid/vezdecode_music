package com.belyakov.vezdecodemusic

import android.app.Application
import com.belyakov.vezdecodemusic.di.logicModule
import com.belyakov.vezdecodemusic.di.repositoriesModule
import com.belyakov.vezdecodemusic.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MusicApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MusicApp)
            modules(viewModelModule, repositoriesModule, logicModule)
        }
    }
}