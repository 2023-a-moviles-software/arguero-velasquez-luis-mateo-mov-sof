package com.example.moviles_soft_01

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RRecyclerAdapter(
    private val context:Context,
    private val list:ArrayList<BTrainer>,
    private val recyclerView: RecyclerView
):RecyclerView.Adapter<RRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nameTextView: TextView;
        val ciTextView: TextView;
        val likesTextView: TextView;
        val likeButton: Button;
        var likeCount: Int = 0;

        init{
            nameTextView = view.findViewById(R.id.tv_nombre)
            ciTextView = view.findViewById(R.id.tv_cedula)
            likesTextView = view.findViewById(R.id.tv_likes)
            likeButton = view.findViewById(R.id.btn_like)

            likeButton.setOnClickListener{ likePost() }

        }
            fun likePost(){
                likeCount += 1
                likesTextView.setText(likeCount.toString())
                (context as FRecyclerView).increaseLikeCount()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.recycler_view_lista,
                parent,
                false
            )

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTrainer = this.list[position]
        holder.nameTextView.setText(currentTrainer.nombre)
        holder.ciTextView.setText(currentTrainer.description)
        holder.likeButton.setText("${currentTrainer.id} 👍")
        holder.likesTextView.setText("0")
    }
}