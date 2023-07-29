package com.luism.tarearecycler

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.Instant

class SmallVideoItemAdapter(
    private val context: Context,
    private val videos: List<Video>
): RecyclerView.Adapter<SmallVideoItemAdapter.VideoHolder>() {
    inner class VideoHolder(view: View): RecyclerView.ViewHolder(view){
        val thumbnailImageView = view.findViewById<ImageView>(R.id.iv_thumbnail)
        val titleTextView = view.findViewById<TextView>(R.id.tv_title)
        val descriptionTextView = view.findViewById<TextView>(R.id.tv_descriptions)
        val durationTextView = view.findViewById<TextView>(R.id.tv_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.small_video_item,
                parent,
                false
            )
        return VideoHolder(itemView)
    }

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        val video = this.videos[position]

        setViewValues(holder,video)
    }

    fun setViewValues(holder: VideoHolder, video:Video){
        val secondsAgo:Long = (Instant.now().epochSecond - video.uploadTime.epochSecond)
        val timeAgo = Misc.secondsToReadableTime(secondsAgo)
        val viewsCount = Misc.countToHumanReadable(video.views.toLong())

        holder.titleTextView.text = video.title
        holder.descriptionTextView.text = "$viewsCount views • $timeAgo ago"
        holder.durationTextView.text = DateUtils.formatElapsedTime(video.lengthSeconds.toLong())

        holder.thumbnailImageView.setImageResource(context.resources.getIdentifier("_${
            Misc.md5Hex(
                video.title
            )
        }","drawable",context.packageName))
    }
}