package epn.mov.bakery.model

import jdk.jfr.Timespan
import src.epn.mov.bakery.model.Bread
import java.time.Instant
import java.time.temporal.TemporalAmount
import java.util.Date
import java.util.concurrent.TimeUnit

public class Bakery {
    private var name:String;
    private val breads:HashMap<String, MutableList<Bread>>;

    constructor(name:String){
        this.name = name;
        this.breads = HashMap();
    }

    fun bakeBread(bread:Bread){
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
            .reduce { acc, i -> acc+i }

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


    fun discardBreadOlderThan(maxSellableAge:Int){
        val dateMillis = {date:Date->date.toInstant().toEpochMilli()};
        val todayMillis = dateMillis(Date.from(Instant.now()));

        val daysAgoMillis = {date:Date->todayMillis-dateMillis(date)};
        val daysAgo = {date:Date->TimeUnit.DAYS.convert(daysAgoMillis(date),TimeUnit.MILLISECONDS)};
        val expirePredicate = {date:Date -> daysAgo(date)>maxSellableAge};
        val breadExpiredPredicate = {bread:Bread->expirePredicate(bread.getElaborationDate())}


        breads.forEach { (_, breadList) -> breadList.removeIf(breadExpiredPredicate) }

    }
}
