package com.luism.x2_examen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.luism.x2_examen.util.Infix.Companion.then
import com.luism.x2_examen.R
import epn.mov.bakery.model.Bakery
import kotlin.streams.toList

class BreadAdapter(
    private val context: Context,
    private val bakery: Bakery,
    var keys:List<String> = listOf()
): BaseAdapter() {

    init {
        setKeys()
    }

    private fun setKeys(){
        keys = bakery.getBreads().keys.stream().toList()
    }

    override fun getCount(): Int {
        return bakery.getBreads().size
    }

    override fun getItem(p0: Int): Any {
        return bakery.getBreads()[this.keys[p0]]!!
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong();
    }

    override fun getView(pos: Int, view: View?, viewGroup: ViewGroup?): View {
        val returnView = LayoutInflater.from(context).inflate(R.layout.bread_item,null)
        val breadCount = bakery.getBreadsCount();

        R.id.tv_title.then { returnView.findViewById<TextView>(it)}.
            setText(keys[pos])
        R.id.tv_details.then { returnView.findViewById<TextView>(it) }
            .setText("${breadCount[keys[pos]]} unidades")
        return returnView
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
        setKeys()
    }
}