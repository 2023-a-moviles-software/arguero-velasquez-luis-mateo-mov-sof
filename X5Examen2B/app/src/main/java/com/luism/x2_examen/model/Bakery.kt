@file:JvmName("Bakery")
package epn.mov.bakery.model

import java.time.LocalDate
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap
import kotlin.jvm.Transient

public class Bakery(
){
    var name:String = ""
    var ruc:String = ""
    var address:String = ""

    var collection:MutableMap<String, BreadCollection> = mutableMapOf()


    constructor(name:String,ruc:String,address:String) : this(){
        this.name = name
        this.ruc = ruc
        this.address = address
        this.collection = HashMap()
    }

    fun getBreadsCount():Map<String,Int>{
        val ret =collection
            .mapValues{ it.value.count()}
        return ret
    }

    fun getBreads():Map<String,List<Bread>>{
        return Collections.unmodifiableMap(collection.mapValues { it.value.breads });
    }
    fun bakeBread(bread: Bread){
        val exists = collection.contains(bread.name)

        if(exists) collection[bread.name]!!.breads.add(bread)
        else collection[bread.name] = BreadCollection(mutableListOf(bread))
    }

    fun getByName(name:String):List<Bread>{
        return (collection[name]?.breads)?:listOf();
    }

    fun sellBread(name:String,quantity:Int):Boolean{
        val breadList = getByName(name);
        val availableBread = breadList
            .map {it.stock}
            .reduceOrNull { acc, i -> acc+i }?:0

        if(quantity>availableBread) return false;

        var remainingSells = quantity;
        breadList.forEach {
            val stock = it.stock
            val newStock = if(stock>remainingSells)
                stock-remainingSells
                else 0;

            it.stock = newStock
            remainingSells -= stock-newStock;
        }
        return true;
    }

    fun discardOutOfStock(){
        collection.forEach { (_, u) -> u.breads.removeIf { it.stock==0 } }
    }



    fun isBreadExpired(bread: Bread, maxSellableAge: Int):Boolean{
        val todayLocalDate = LocalDate.now();
        val daysAgo = {date:LocalDate -> date.until(todayLocalDate).days}
        val expirePredicate = {date:LocalDate -> daysAgo(date)>maxSellableAge};
        return expirePredicate(bread.elaborationDate)
    }
    fun discardBreadOlderThan(maxSellableAge:Int){
        val breadExpiredPredicate = {bread: Bread ->isBreadExpired(bread,maxSellableAge)}
        collection.forEach { (_, breadList) -> breadList.breads.removeIf(breadExpiredPredicate) }
    }

    fun discardBreadNamed(breadName:String){
        collection.remove(breadName)
    }

    fun renameBread(oldName:String, newName: String):Boolean{
        if(!collection.containsKey(oldName)) return false
        if(collection.containsKey(newName)) return false

        collection[newName] = collection.remove(oldName)!!
            .apply { renameBread(newName) }
        return true
    }

    override fun toString(): String {
        return "{name:$name,bread_collection:$collection}"
    }


}
