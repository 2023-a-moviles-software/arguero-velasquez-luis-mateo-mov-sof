package com.luism.tarearecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class ChanelView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chanel_view)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_videos)


        val uploader = intent.getStringExtra("uploader")!!
        recyclerView.adapter = SmallVideoItemAdapter(this,
            Video.collection.filter { it.uploader==uploader })
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter!!.notifyDataSetChanged()
    }
}