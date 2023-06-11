@file:JvmName("Bread")
package epn.mov.bakery.model

import kotlinx.serialization.Contextual
import java.time.LocalDate
import kotlinx.serialization.Serializable

@Serializable
public class Bread(
    private val name: String,
    private val price: Double,
    @Serializable(with = KLocalDateSerializer::class) private val elaborationDate: LocalDate,
    private val isSweet: Boolean,
    private var stock: Int
){
    fun getName():String {return this.name};
    fun getStock():Int {return this.stock};
    fun getPrice():Double {return this.price};
    fun isSweet():Boolean {return this.isSweet};
    fun setStock(stock:Int) {this.stock=stock};
    fun getElaborationDate():LocalDate {return this.elaborationDate}

    fun mappify():Map<String,Object>{
        val map = HashMap<String,Object>();
        map["name"] = this.getName() as Object;
        map["price"] = this.getPrice() as Object;
        map["elaborationDate"] = this.getElaborationDate() as Object;
        map["isSweet"] = this.isSweet() as Object;
        map["stock"] = this.getStock() as Object;
        return map;
    }

    companion object {
        fun map(parameters:Map<String,Object>):Bread?{
            val name = parameters["name"] as String;
            val price = parameters["price"] as Double;
            val elaborationDate = LocalDate.parse((parameters["elaborationDate"] as String));
            val isSweet = parameters["isSweet"] as Boolean;
            val stock = parameters["stock"] as Int

            return Bread(name,price,elaborationDate,isSweet,stock)
        }
    }
}