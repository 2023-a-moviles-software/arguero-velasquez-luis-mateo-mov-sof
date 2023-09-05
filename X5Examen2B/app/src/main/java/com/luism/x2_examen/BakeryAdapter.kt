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

class BakeryAdapter(
    private val context: Context,
    private val bakeries: Map<String,Bakery>,
): BaseAdapter() {
    lateinit var keys:List<String>
    init{
        setKeys()
    }

    override fun getCount(): Int = keys.size

    override fun getItem(p0: Int): Any = bakeries[keys[p0]]!!

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(pos: Int, oldView: View?, viewGroup: ViewGroup?): View {
        val returnView = LayoutInflater.from(context).inflate(R.layout.bread_item,null)

        R.id.tv_title.then { returnView.findViewById<TextView>(it)}.
        setText(keys[pos])
        R.id.tv_details.then { returnView.findViewById<TextView>(it) }
            .setText("${bakeries[keys[pos]]!!.address} (${bakeries[keys[pos]]!!.ruc})")
        return returnView
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
        setKeys()
    }

    fun setKeys(){
        this.keys = bakeries.keys.toList()
    }
}