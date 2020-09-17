package com.belyakov.vezdecodemusic.presentation.setup.views

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.utils.dpToPx
import com.belyakov.vezdecodemusic.utils.roundedTransform
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.v_load_photo.view.*


class LoadImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.v_load_photo, this)
    }

    fun loadPhoto(uri: Uri?) {
        Glide
            .with(context)
            .load(uri)
            .roundedTransform(context)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageContainer)

        placeholderIcon.isVisible = false
        imageContainer.isVisible = true
    }
}