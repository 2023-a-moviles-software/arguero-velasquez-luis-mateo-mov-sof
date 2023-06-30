package com.luism.x2_examen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.luism.x2_examen.persistence.SingletonManager
import com.luism.x2_examen.util.Infix.Companion.then
import epn.mov.bakery.model.Bread
import java.text.DecimalFormat

class Details : AppCompatActivity() {
    var breadName:String? = null;
    var batchAdapter:BatchAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        var breadName = intent.getStringExtra("name")!!
        batchAdapter = BatchAdapter(this,SingletonManager.getBakery().getBreads()[breadName]!!)

        R.id.tv_breadName.then<Int,TextView>(::findViewById)
            .setText(breadName)

        val breadCount = SingletonManager.getBakery().getBreadsCount()[breadName!!]
        R.id.tv_bread_count.then<Int,TextView>(::findViewById)
            .setText("$breadCount unidades")

        val highestPrice = SingletonManager
            .getBakery()
            .getBreads()[breadName!!]!!
            .map { it.getPrice() }
            .max()
            .then { DecimalFormat("##0.00").format(it) }
        R.id.tv_price.then<Int,TextView>(::findViewById)
            .setText("$$highestPrice")

        R.id.lv_batches.then<Int,ListView>(::findViewById)
            .adapter = batchAdapter
        batchAdapter!!.notifyDataSetChanged()

        R.id.fab_delete.then<Int,FloatingActionButton>(::findViewById)
            .setOnClickListener{
                SingletonManager.getBakery().discardBreadNamed(breadName)
                finish()
            }
    }
}