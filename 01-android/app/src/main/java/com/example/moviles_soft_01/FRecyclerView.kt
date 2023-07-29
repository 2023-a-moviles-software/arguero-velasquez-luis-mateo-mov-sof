package com.example.moviles_soft_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerView : AppCompatActivity() {
    var totalLikes = 0;
    var arreglo = BBaseDatosMemoria.trainers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frecycler_view)

    }

    fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_trainers)

        recyclerView.adapter = RRecyclerAdapter(this,arreglo,recyclerView)
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()

        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    fun increaseLikeCount(){
        totalLikes += 1
        val totalLikes = findViewById<TextView>(R.id.tv_likes)
        totalLikes.text = totalLikes.toString()
    }
}