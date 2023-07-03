@file:JvmName("Bakery")
package epn.mov.bakery.model

import java.time.LocalDate
import java.util.*
import kotlin.collections.HashMap

@kotlinx.serialization.Serializable
class Bakery(
    var name:String,
    val ruc:String,
    var address:String,

    protected var breads:HashMap<String, MutableList<Bread>>
){

    constructor(name:String,ruc:String,address:String) : this(name,ruc,address,HashMap())

    fun getBreadsCount():Map<String,Int>{
        return breads.mapValues { it.value.sumOf { bread -> bread.getStock() } }
    }

    fun getBreads():Map<String,MutableList<Bread>>{
        return breads
    }
    fun bakeBread(bread: Bread){
        val exists = breads.contains(bread.name)

        if(exists) breads[bread.name]!!.add(bread)
        else breads[bread.name] = mutableListOf(bread)
    }

    fun getByName(name:String):List<Bread>{
        return breads[name]?:listOf()
    }

    fun sellBread(name:String,quantity:Int):Boolean{
        val breadList = getByName(name)
        val availableBread = breadList
            .map {it.getStock()}
            .reduceOrNull { acc, i -> acc+i }?:0

        if(quantity>availableBread) return false

        var remainingSells = quantity
        breadList.forEach {
            val stock = it.getStock()
            val newStock = if(stock>remainingSells)
                stock-remainingSells
                else 0

            it.setStock(newStock)
            remainingSells -= stock-newStock
        }
        return true
    }

    fun discardOutOfStock(){
        breads.forEach { (_, u) -> u.removeIf { it.getStock()==0 } }
    }

    fun discardBreadNamed(breadName:String):Boolean{
        return breads.remove(breadName)==null
    }

    fun discardBreadOlderThan(maxSellableAge:Int){
        breads.forEach { (_, breadList) -> breadList.removeIf(getIsBreadExpiredPredicate(maxSellableAge)) }

    }

    fun renameBread(old:String, new:String):Boolean{
        if(breads.containsKey(new)) return false
        if(!breads.containsKey(old)) return false

        val affectedBreads = breads.remove(old)!!.also{breads[new]=it}
        affectedBreads.forEach { it.name = new }

        return true
    }

    companion object{
        fun getIsBreadExpiredPredicate(maxSellableAge:Int): (Bread)->Boolean {
            return {bread: Bread ->isBreadExpired(bread,maxSellableAge)}
        }

        fun isBreadExpired(bread: Bread, maxSellableAge: Int):Boolean{
            val todayLocalDate = LocalDate.now()
            val daysAgo = {date:LocalDate -> date.until(todayLocalDate).days}
            val expirePredicate = {date:LocalDate -> daysAgo(date)>maxSellableAge}
            return expirePredicate(bread.getElaborationDate())
        }
    }
}
