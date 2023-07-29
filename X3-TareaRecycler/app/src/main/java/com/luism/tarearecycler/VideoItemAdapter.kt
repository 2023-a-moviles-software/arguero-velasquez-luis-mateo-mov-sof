package com.luism.tarearecycler

import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import android.util.Log
import android.util.TimeUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.luism.tarearecycler.Misc.Companion.countToHumanReadable
import com.luism.tarearecycler.Misc.Companion.md5Hex
import com.luism.tarearecycler.Misc.Companion.secondsToReadableTime
import java.math.BigInteger
import java.security.MessageDigest
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Date

class VideoItemAdapter(
    private val context:Context,
    var videos:ArrayList<Video>
): RecyclerView.Adapter<VideoItemAdapter.VideoHolder>() {
    inner class VideoHolder(view: View):ViewHolder(view){
        val uploaderImageView = view.findViewById<ImageView>(R.id.iv_uploader)
        val thumbnailImageView = view.findViewById<ImageView>(R.id.iv_thumbnail)
        val titleTextView = view.findViewById<TextView>(R.id.tv_title)
        val descriptionTextView = view.findViewById<TextView>(R.id.tv_descriptions)
        val durationTextView = view.findViewById<TextView>(R.id.tv_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.video_item,
                parent,
                false
            )
        return VideoHolder(itemView)
    }

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        val video = this.videos[position]

        setViewValues(holder,video)
        holder.uploaderImageView.setOnClickListener{
            val intent = Intent(context,ChanelView::class.java)
                .putExtra("uploader",videos[position].uploader)
            context.startActivity(intent)
        }

        holder.thumbnailImageView.setOnClickListener{
            addView(video)
        }

    }

    fun setViewValues(holder: VideoHolder, video:Video){

        val secondsAgo:Long = (Instant.now().epochSecond - video.uploadTime.epochSecond)
        holder.titleTextView.text = video.title
        holder.descriptionTextView.text = "${video.uploader} • ${countToHumanReadable(video.views.toLong())} views • ${secondsToReadableTime(secondsAgo)} ago"
        holder.durationTextView.text = DateUtils.formatElapsedTime(video.lengthSeconds.toLong())

        holder.uploaderImageView.setImageResource(context.resources.getIdentifier("_${md5Hex(video.uploader)}","drawable",context.packageName))
        holder.thumbnailImageView.setImageResource(context.resources.getIdentifier("_${md5Hex(video.title)}","drawable",context.packageName))
    }

    fun addView(video: Video){
        video.views += 1
        notifyDataSetChanged()
    }
}