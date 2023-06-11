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
        private var bakery:Bakery? = null;
        fun initBakery(){
            var bakery = loadBakery();
            if (bakery==null){
                bakery = Bakery("Los coco")
            }
            this.bakery=bakery;
        }

        private fun readInstanceFile():String?{
            return try {
                File("instance.json").readText();
            } catch (v:FileNotFoundException){
                null;
            }

        }
        private fun parseJsonToObject(string:String):Bakery{
            return Json.decodeFromString(string);
        }

        fun loadBakery():Bakery?{
            val jsonString = readInstanceFile() ?: return null
            return parseJsonToObject(jsonString!!)
        }

        fun getBakery():Bakery{
            if(bakery==null) initBakery()
            return bakery!!;
        }

        fun parseBakeryToJson(bakery: Bakery):String{
            return Json.encodeToString(bakery);
        }

        fun saveBakery(bakeryString: String){
            File("instance.json").writeText(bakeryString)
        }

        fun setBakery(bakery:Bakery){
            this.bakery = bakery;
            val bakeryString = parseBakeryToJson(bakery);
            saveBakery(bakeryString)
        }
    }


}