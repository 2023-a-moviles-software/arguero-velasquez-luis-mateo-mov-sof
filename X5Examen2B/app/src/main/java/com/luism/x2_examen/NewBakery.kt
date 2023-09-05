package com.luism.x2_examen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.luism.x2_examen.persistence.SingletonManager
import com.luism.x2_examen.util.Infix.Companion.then
import epn.mov.bakery.model.Bakery

class NewBakery : AppCompatActivity() {
    private var bakeryName:String? = null
    private var actionFunction:(String,String,String)->Boolean = ::createBakery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_bakery)

        bakeryName = intent.getStringExtra("bakeryName")
        if(bakeryName!=null){
            loadOldBakery()
        }

        R.id.btn_create.then { findViewById<Button>(it) }
            .setOnClickListener {
                if (performAction()) finish()
            }
    }

    fun performAction():Boolean{
        try{
            val name= R.id.intxt_name.then { findViewById<EditText>(it) }.text.toString()
            val ruc= R.id.intxt_ruc.then { findViewById<EditText>(it) }.text.toString()
            val address = R.id.intxt_address.then { findViewById<EditText>(it) }.text.toString()


            return actionFunction(name,ruc,address)
        }catch (e:IllegalArgumentException){
            return false
        }
    }

    fun createBakery(name:String,ruc:String,address:String):Boolean{
        if(SingletonManager.bakeries.keys.contains(name)) return false
        SingletonManager.bakeries[name] = Bakery(name,ruc,address)
        return true
    }

    fun updateBakery(newName:String,ruc:String,address:String):Boolean{
        if(SingletonManager.bakeries.keys.contains(newName)
         and (newName != bakeryName)) return false

        SingletonManager.bakeries.remove(bakeryName)!!
            .also { it.name = newName }
            .also { it.address = address }
            .also { SingletonManager.bakeries[newName] = it }
        return true
    }

    fun loadOldBakery(){
        R.id.intxt_ruc.then { findViewById<EditText>(it) }.isEnabled = false
        actionFunction = ::updateBakery
        populateFields(bakeryName!!)
        R.id.btn_create.then { findViewById<Button>(it) }.setText("Actualizar")
        R.id.tv_action.then { findViewById<TextView>(it) }.setText("Actualizar Panadería")
    }

    fun populateFields(bakeryName:String){
        val bakery = SingletonManager.bakeries[bakeryName]!!
        R.id.intxt_name.then { findViewById<EditText>(it) }.setText(bakeryName)
        R.id.intxt_ruc.then { findViewById<EditText>(it) }.setText(bakery.ruc)
        R.id.intxt_address.then { findViewById<EditText>(it) }.setText(bakery.address)
    }
}
