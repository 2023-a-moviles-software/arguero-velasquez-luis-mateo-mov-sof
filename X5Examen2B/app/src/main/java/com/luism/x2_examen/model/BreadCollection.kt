package epn.mov.bakery.model

import java.time.LocalDate


class BreadCollection() {
    var id: Long? = null

    var breads: MutableList<Bread> = mutableListOf()

    constructor(breads: MutableList<Bread>) : this(){
        this.breads = breads
    }

    fun removeExpired(maxAge:Long){
        breads.removeIf {  it.elaborationDate < LocalDate.now().minusDays(maxAge) }
    }

    fun renameBread(newName:String){
        breads.forEach{ it.name = newName }
    }

    fun count():Int{
        return breads.map { it.stock }.stream().mapToInt { it }.sum()
    }

    override fun toString(): String {
        return breads.toString()
    }
}