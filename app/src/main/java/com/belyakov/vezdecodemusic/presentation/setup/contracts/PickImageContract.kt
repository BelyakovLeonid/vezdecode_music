package com.belyakov.vezdecodemusic.presentation.setup.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import com.belyakov.vezdecodemusic.R

class PickImageContract : ActivityResultContract<Unit, Uri?>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                type = "image/*"
            }

        return Intent.createChooser(intent, context.getString(R.string.pick_image))
    }

    override fun parseResult(resultCode: Int, intent: Intent?) =
        intent?.data
}
