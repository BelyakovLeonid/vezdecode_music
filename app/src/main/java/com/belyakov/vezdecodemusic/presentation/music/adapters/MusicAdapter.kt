package com.belyakov.vezdecodemusic.presentation.music.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.data.models.MusicFile
import com.belyakov.vezdecodemusic.utils.roundedTransform
import com.belyakov.vezdecodemusic.utils.toTimeString
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class MusicAdapter(
    private val onChooseListener: (MusicFile) -> Unit
) : RecyclerView.Adapter<MusicViewHolder>() {

    private var data: List<MusicFile> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MusicViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.v_music_item, parent, false)
    )

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(data[position], onChooseListener)
    }

    override fun getItemCount(): Int = data.size

    fun submitData(list: List<MusicFile>) {
        data = list
        notifyDataSetChanged()
    }
}

class MusicViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    private val root = item.findViewById<ConstraintLayout>(R.id.root)
    private val icon = item.findViewById<ImageView>(R.id.musicIcon)
    private val name = item.findViewById<TextView>(R.id.audioName)
    private val author = item.findViewById<TextView>(R.id.audioAuthor)
    private val duration = item.findViewById<TextView>(R.id.audioDuration)

    fun bind(musicFile: MusicFile, onClickListener: (MusicFile) -> Unit) {
        setIcon(musicFile.imageRes)
        name.text = musicFile.name
        author.text = musicFile.author
        duration.text = musicFile.duration.toTimeString()
        root.setOnClickListener {
            onClickListener.invoke(musicFile)
        }
    }

    private fun setIcon(imageRes: Int) {
        Glide
            .with(itemView.context)
            .load(imageRes)
            .roundedTransform(itemView.context)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(icon)
    }
}