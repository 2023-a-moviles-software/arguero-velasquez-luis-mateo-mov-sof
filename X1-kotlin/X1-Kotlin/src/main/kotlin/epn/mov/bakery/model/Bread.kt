package src.epn.mov.bakery.model

import java.util.Date

public class Bread(
    private val name: String,
    private val price: Double,
    private val elaborationDate: Date,
    private val isSweet: Boolean,
    private var stock: Int
){
    val getName = { ->this.name};
    val getStock = { ->this.stock};
    val getElaborationDate = { ->this.elaborationDate};
    val setStock = { stock:Int -> this.stock = stock};
}