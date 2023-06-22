package com.example.moviles_soft_01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.moviles_soft_01.R.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val btnACicloVida = findViewById<Button>(id.btn_ciclo_vida)
        val btnBListView = findViewById<Button>(id.btn_list_view)
        btnACicloVida.setOnClickListener { irActividad(AACicloVida::class.java) }
        btnBListView.setOnClickListener { irActividad(BListView::class.java) }
    }

    fun irActividad(clazz:Class<*>){
        val intent = Intent(this,clazz)
        startActivity(intent)
    }
}