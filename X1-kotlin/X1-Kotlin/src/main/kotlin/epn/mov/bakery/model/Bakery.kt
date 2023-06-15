@file:JvmName("Bakery")
package epn.mov.bakery.model

import java.time.LocalDate
import java.util.*

@kotlinx.serialization.Serializable
public class Bakery(
    private var name:String,
    private var ruc:String,
    private var address:String,
    protected val breads:HashMap<String, MutableList<Bread>>
){

    constructor(name:String,ruc:String,address:String) : this(name,ruc,address,HashMap())
    fun getName():String{
        return name;
    }

    fun setName(name:String){
        this.name = name;
    }

    fun getBreadsCount():Map<String,Int>{
        return breads.mapValues { it.value.sumOf { bread -> bread.getStock() } }
    }

    fun getBreads():Map<String,MutableList<Bread>>{
        return Collections.unmodifiableMap(breads);
    }
    fun bakeBread(bread: Bread){
        val exists = breads.contains(bread.getName())

        if(exists) breads[bread.getName()]!!.add(bread)
        else breads[bread.getName()] = mutableListOf(bread)
    }

    fun getByName(name:String):List<Bread>{
        return breads[name]?:listOf();
    }

    fun sellBread(name:String,quantity:Int):Boolean{
        val breadList = getByName(name);
        val availableBread = breadList
            .map {it.getStock()}
            .reduceOrNull { acc, i -> acc+i }?:0

        if(quantity>availableBread) return false;

        var remainingSells = quantity;
        breadList.forEach {
            val stock = it.getStock()
            val newStock = if(stock>remainingSells)
                stock-remainingSells
                else 0;

            it.setStock(newStock)
            remainingSells -= stock-newStock;
        }
        return true;
    }

    fun discardOutOfStock(){
        breads.forEach { (_, u) -> u.removeIf { it.getStock()==0 } }
    }



    fun isBreadExpired(bread: Bread, maxSellableAge: Int):Boolean{
        val todayLocalDate = LocalDate.now();
        val daysAgo = {date:LocalDate -> date.until(todayLocalDate).days}
        val expirePredicate = {date:LocalDate -> daysAgo(date)>maxSellableAge};
        return expirePredicate(bread.getElaborationDate())
    }
    fun discardBreadOlderThan(maxSellableAge:Int){
        //val dateMillis = {date: LocalDate ->date};
        //val todayMillis = dateMillis(LocalDate.from(Instant.now()));

        //val daysAgoMillis = {date:LocalDate->todayMillis-dateMillis(date)};
        //val daysAgo = {date:LocalDate->TimeUnit.DAYS.convert(daysAgoMillis(date),TimeUnit.MILLISECONDS)};
        //val expirePredicate = {date:LocalDate -> daysAgo(date)>maxSellableAge};
        val breadExpiredPredicate = {bread: Bread ->isBreadExpired(bread,maxSellableAge)}



        breads.forEach { (_, breadList) -> breadList.removeIf(breadExpiredPredicate) }

    }

    fun mappify():Map<String,Object>{
        val map = HashMap<String,Object>();
        map["name"] = getName() as Object;
        map["bread"] = getBreads().mapValues { it.value.map { bread->bread.mappify() } } as Object
        return map;
    };

    companion object{
        fun map(parameters:Map<String,Object>):Bakery?{
            val name = parameters["name"] as String
            val ruc = parameters["ruc"] as String
            val address = parameters["address"] as String
            val breads:HashMap<String,MutableList<Bread>> = HashMap<String,MutableList<Bread>>(
                (parameters["breads"] as Map<String,List<Map<String,Object>>>)
                    .mapValues {
                        val breadList= it.value.mapNotNull { bread -> Bread.map(bread) }
                            .toTypedArray()
                        return@mapValues mutableListOf(*breadList);
                    }
            )
            return Bakery(name,ruc,address,breads);
        }
    }

}
