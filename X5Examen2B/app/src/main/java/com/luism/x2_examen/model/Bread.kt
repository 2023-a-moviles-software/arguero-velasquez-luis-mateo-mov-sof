@file:JvmName("Bread")
package epn.mov.bakery.model

import java.time.LocalDate

public class Bread(){

    var id: Long? = null

    var name: String = "";
    var price: Double = 0.0
    var elaborationDate: LocalDate = LocalDate.MIN
    var isSweet: Boolean = false
    var stock: Int = 0

    constructor(
        name: String,
        price: Double,
        elaborationDate: LocalDate,
        isSweet: Boolean,
        stock: Int) : this() {

        this.name = name
        this.price = price
        this.elaborationDate = elaborationDate
        this.isSweet = isSweet
        this.stock = stock
    }

    override fun toString(): String {
        return "Bread(id=$id, name='$name', price=$price, elaborationDate=$elaborationDate, isSweet=$isSweet, stock=$stock)"
    }


}