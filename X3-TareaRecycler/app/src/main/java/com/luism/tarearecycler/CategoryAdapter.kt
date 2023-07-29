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

class CategoryAdapter (
private val context: Context,
private val categories: List<String>,
private val onSelectCallback: (String)->Unit
): RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {
    inner class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryTextView = view.findViewById<TextView>(R.id.tv_text) // TODO: Replace
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.category_item,
                parent,
                false
            )
        return CategoryHolder(itemView)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val category = this.categories[position]
        holder.categoryTextView.setOnClickListener{
            onSelectCallback(category)
        }

        setViewValues(holder, category)
    }

    fun setViewValues(holder: CategoryHolder, category: String) {
        holder.categoryTextView.text = category
    }
}