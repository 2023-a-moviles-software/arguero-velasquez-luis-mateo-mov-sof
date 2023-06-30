package com.luism.x2_examen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.luism.x2_examen.util.Infix.Companion.then
import epn.mov.bakery.model.Bakery
import epn.mov.bakery.model.Bread
import org.w3c.dom.Text
import kotlin.streams.toList

class BatchAdapter(
    private val context: Context,
    private val breads: List<Bread>,
): BaseAdapter() {

    override fun getCount(): Int {
        return breads.size
    }

    override fun getItem(p0: Int): Any {
        return breads[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong();
    }

    override fun getView(pos: Int, view: View?, viewGroup: ViewGroup?): View {
        val returnView = LayoutInflater.from(context).inflate(R.layout.batch_item,null)
        val bread = breads[pos]

        R.id.tv_title.then{returnView.findViewById<TextView>(it)}.text =
            bread.getElaborationDate().toString()

        R.id.tv_details.then{returnView.findViewById<TextView>(it)}.text =
            """
                Precio: ${bread.getPrice()}
                Stock: ${bread.getStock()}
                Es dulce: ${bread.isSweet()}
            """.trimIndent()

        return returnView
    }

}