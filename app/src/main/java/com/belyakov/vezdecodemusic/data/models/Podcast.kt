package com.belyakov.vezdecodemusic.data.models

import android.net.Uri

data class Podcast(
    var name: String? = null,
    var description: String? = null,
    var imageUri: Uri? = null,
    var audioUri: Uri? = null,
    var tagContent: Boolean = false,
    var tagExport: Boolean = false,
    var tagTrailer: Boolean = false,
    var visibility: Boolean = false,
    var timecodes: List<Timecode> = listOf()
) {
}