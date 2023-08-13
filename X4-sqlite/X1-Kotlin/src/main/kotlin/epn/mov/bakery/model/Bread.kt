@file:JvmName("Bread")
package epn.mov.bakery.model

import jakarta.persistence.*
import kotlinx.serialization.Contextual
import java.time.LocalDate
import kotlinx.serialization.Serializable

@Entity
@Table(name = "Bread")
public class Bread(){

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false) var name: String = "";
    @Column(nullable = false) var price: Double = 0.0
    @Column(nullable = false) var elaborationDate: LocalDate = LocalDate.MIN
    @Column(nullable = false) var isSweet: Boolean = false
    @Column(nullable = false) var stock: Int = 0

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