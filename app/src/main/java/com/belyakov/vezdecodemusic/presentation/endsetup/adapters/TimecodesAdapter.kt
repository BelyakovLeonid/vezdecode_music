package com.belyakov.vezdecodemusic.presentation.endsetup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.data.models.Timecode
import com.belyakov.vezdecodemusic.utils.toTimeString

class TimecodesAdapter : RecyclerView.Adapter<TimecodeViewHolder>() {

    private var data: List<Timecode>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TimecodeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.v_timecode_item, parent, false)
    )

    override fun onBindViewHolder(holder: TimecodeViewHolder, position: Int) {
        holder.bind(data?.getOrNull(position))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    fun submitList(timecodes: List<Timecode>){
        data = timecodes
        notifyDataSetChanged()
    }

}

class TimecodeViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    private val timeView = item.findViewById<TextView>(R.id.time)
    private val nameView = item.findViewById<TextView>(R.id.name)

    fun bind(timecode: Timecode?) {
        timeView.text = timecode?.timeMillis?.toTimeString()
        nameView.text = itemView.context.getString(R.string.name_template, timecode?.name)
    }
}