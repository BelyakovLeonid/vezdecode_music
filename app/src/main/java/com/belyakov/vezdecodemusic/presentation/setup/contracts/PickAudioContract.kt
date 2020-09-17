package com.belyakov.vezdecodemusic.presentation.setup.contracts

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class PickAudioContract : ActivityResultContract<Unit, Uri?>() {
    override fun createIntent(context: Context, input: Unit?) =
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "audio/*"
        }

    override fun parseResult(resultCode: Int, intent: Intent?) =
        intent?.data
}

