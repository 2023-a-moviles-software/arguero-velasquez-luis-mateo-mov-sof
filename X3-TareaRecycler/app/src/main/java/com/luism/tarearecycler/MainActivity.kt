package com.luism.tarearecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
    }

    fun initRecyclerView(){
        val recyclerViewVideos = findViewById<RecyclerView>(R.id.rv_videos)
        val videoAdapter = VideoItemAdapter(this,Video.collection)

        recyclerViewVideos.adapter = videoAdapter
        recyclerViewVideos.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerViewVideos.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerViewVideos.adapter!!.notifyDataSetChanged()

        val homeImageView:ImageView = findViewById(R.id.iv_home)
        homeImageView.setOnClickListener{
            Video.collection.shuffle()
            recyclerViewVideos.adapter!!.notifyDataSetChanged()
        }

        val categoryAdapter = CategoryAdapter(this, listOf("All","Gaming","Sitcoms","Animated films","Mixes","Music","Team Fortress 2")
        ) {
            chosenTag->
            val videosArray = Video.collection.filter { video->video.tags.any(chosenTag::equals) }
            videoAdapter.videos = ArrayList(videosArray)
            videoAdapter.notifyDataSetChanged()
        }

        val recyclerViewCategories = findViewById<RecyclerView>(R.id.rv_categories)
        recyclerViewCategories.adapter = categoryAdapter
        recyclerViewCategories.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerViewCategories.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerViewCategories.adapter!!.notifyDataSetChanged()
    }
}