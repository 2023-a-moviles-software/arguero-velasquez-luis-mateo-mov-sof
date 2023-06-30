@file:JvmName("Bread")
package epn.mov.bakery.model

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.Contextual
import java.time.LocalDate
import kotlinx.serialization.Serializable

@Serializable
public data class Bread(
    var name: String,
    private val price: Double,
    @Serializable(with = KLocalDateSerializer::class) private val elaborationDate: LocalDate,
    private val isSweet: Boolean,
    private var stock: Int
){
    fun getStock():Int {return this.stock};
    fun getPrice():Double {return this.price};
    fun isSweet():Boolean {return this.isSweet};
    fun setStock(stock:Int) {this.stock=stock};
    fun getElaborationDate():LocalDate {return this.elaborationDate}

}