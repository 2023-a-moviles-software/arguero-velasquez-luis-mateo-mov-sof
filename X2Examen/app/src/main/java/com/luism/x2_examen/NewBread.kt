package com.luism.x2_examen

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.luism.x2_examen.persistence.SingletonManager
import com.luism.x2_examen.util.Infix.Companion.then
import epn.mov.bakery.model.Bakery
import epn.mov.bakery.model.Bread
import java.time.LocalDate
@RequiresApi(Build.VERSION_CODES.O)

class NewBread : AppCompatActivity() {
    var bakery:Bakery? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_bread)

        val bakeryName = intent.getStringExtra("bakeryName")
        this.bakery = SingletonManager.bakeries[bakeryName]

        R.id.btn_bake.then<Int, Button>(::findViewById)
            .setOnClickListener {
                if(createBread())
                    finish()
            }
    }

    fun createBread():Boolean{
        try {
            val name:String = findViewById<TextInputEditText>(R.id.intxt_bread_name).text.toString()
            val price:Double = findViewById<TextInputEditText>(R.id.intxt_bread_price).text.toString()
                .toDouble()
            val stock:Int = findViewById<TextInputEditText>(R.id.intxt_bread_stock).text.toString()
                .toInt()
            val elaborationDate: LocalDate = findViewById<TextInputEditText>(R.id.intxt_bread_date).text.toString()
                .replace("/","-")
                .then(LocalDate::parse)
            val isSweet:Boolean = findViewById<CheckBox>(R.id.cb_sweet).isChecked
            Bread(name, price, elaborationDate, isSweet, stock)
                .then { bakery!!.bakeBread(it) }
            return true
        } catch (e:IllegalArgumentException){
            return false
        }

    }


}