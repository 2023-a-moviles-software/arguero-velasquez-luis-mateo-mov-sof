package epn.mov.bakery.model

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDate
import java.util.*
class SingletonManager {

    companion object{
        private var bakeryName:String = "instance";
        private var bakery:Bakery = Bakery(bakeryName,"","");

        fun setContext(bakeryName:String):Boolean{
            this.bakeryName = bakeryName;
            initBakery()
            return File("$bakeryName.json").isFile;
        }

        private fun initBakery(){
            var bakery = loadBakery();
            if (bakery!=null){
                this.bakery = bakery
            }
        }

        private fun loadBakery():Bakery?{
            val jsonString = try { File("$bakeryName.json").readText()}
                catch (v:FileNotFoundException){ return null }
            return Json.decodeFromString(jsonString!!)
        }

        fun getBakery():Bakery{
            return bakery!!;
        }

        fun setBakery(bakery:Bakery){
            this.bakery = bakery;
            val bakeryString = Json.encodeToString(bakery);
            File("${bakery.getName()}.json").writeText(bakeryString)
        }
    }


}