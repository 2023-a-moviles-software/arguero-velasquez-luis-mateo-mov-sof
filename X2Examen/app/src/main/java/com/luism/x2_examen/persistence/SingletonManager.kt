package com.luism.x2_examen.persistence

import android.content.Context
import com.luism.x2_examen.util.Infix.Companion.hold
import epn.mov.bakery.model.Bakery
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException

class SingletonManager {
    companion object{

        private var bakeryName:String = "instance";
        private var bakery: Bakery = Bakery(bakeryName,"","");
        private var context: Context? = null;

        fun setContext(bakeryName:String,context:Context):Boolean{
            Companion.bakeryName = bakeryName;
            this.context = context;
            initBakery()
            try{
                context.openFileInput("$bakeryName.json1").also{it.read()}.close()
                return true
            } catch(e:FileNotFoundException){
                return false
            };
        }

        private fun initBakery(){
            var bakery = loadBakery();
            if (bakery!=null){
                Companion.bakery = bakery
            }
        }

        private fun loadBakery(): Bakery?{
//            return Json.decodeFromString("\n" +
//                    "{\"name\":\"mi_panaderia\",\"ruc\":\"1234567897102\",\"address\":\"Bartolome Sanchez, Quito 170144\",\"breads\":{\"Rosquilla\":[{\"name\":\"Rosquilla\",\"price\":0.3,\"elaborationDate\":\"2023-06-01\",\"isSweet\":true,\"stock\":20}],\"Popular\":[{\"name\":\"Popular\",\"price\":0.13,\"elaborationDate\":\"2023-06-01\",\"isSweet\":false,\"stock\":13}],\"Empanada de pinia\":[{\"name\":\"Empanada de pinia\",\"price\":0.23,\"elaborationDate\":\"2023-06-01\",\"isSweet\":true,\"stock\":10}],\"Pinia\":[{\"name\":\"Pinia\",\"price\":0.3,\"elaborationDate\":\"2023-06-01\",\"isSweet\":true,\"stock\":15}],\"Manito\":[{\"name\":\"Manito\",\"price\":0.15,\"elaborationDate\":\"2023-06-01\",\"isSweet\":false,\"stock\":20}],\"Croissant\":[{\"name\":\"Croissant\",\"price\":0.3,\"elaborationDate\":\"2023-06-11\",\"isSweet\":false,\"stock\":30}],\"Cupcake\":[{\"name\":\"Cupcake\",\"price\":0.5,\"elaborationDate\":\"2023-06-01\",\"isSweet\":true,\"stock\":20}],\"Empanada\":[{\"name\":\"Empanada\",\"price\":0.25,\"elaborationDate\":\"2023-06-01\",\"isSweet\":false,\"stock\":30}],\"Chocolate\":[{\"name\":\"Chocolate\",\"price\":0.2,\"elaborationDate\":\"2023-06-01\",\"isSweet\":false,\"stock\":13}],\"Pan de Agua\":[{\"name\":\"Pan de Agua\",\"price\":0.13,\"elaborationDate\":\"2023-06-01\",\"isSweet\":false,\"stock\":12}]}}")
            val jsonString = try{
                context!!.openFileInput("$bakeryName.json")!!
                    .bufferedReader()
                    .hold({it.readText()},{it.close()})
            } catch(e:FileNotFoundException){
                ""
            };
            return Json.decodeFromString(jsonString!!)
        }

        fun getBakery(): Bakery {
            return bakery;
        }

        fun setBakery(bakery: Bakery){
            Companion.bakery = bakery;
            save()
        }

        fun save(){
            val bakeryString = Json.encodeToString(bakery);
            context!!.openFileOutput("$bakeryName.json",Context.MODE_PRIVATE)!!.bufferedWriter()
                .also {it.write(bakeryString)}
                .close()
        }
    }


}