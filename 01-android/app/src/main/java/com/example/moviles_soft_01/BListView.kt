package com.example.moviles_soft_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import android.widget.ListView

class BListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val trainerArray = BBaseDatosMemoria.trainers;
        val selectedId = 0;

        fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val listView = findViewById<ListView>(R.id.tv_list_view)

            val lvAdaptar = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                trainerArray
            )

            listView.adapter = lvAdaptar;
            lvAdaptar.notifyDataSetChanged();
        }

    }
}