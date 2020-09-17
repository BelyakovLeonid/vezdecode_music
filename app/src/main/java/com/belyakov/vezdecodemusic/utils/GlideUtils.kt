package com.belyakov.vezdecodemusic.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun RequestBuilder<Drawable>.roundedTransform(context: Context): RequestBuilder<Drawable> {
    val radius = context.dpToPx(10).toInt()
    return transform(MultiTransformation(CenterCrop(), RoundedCorners(radius)))
}