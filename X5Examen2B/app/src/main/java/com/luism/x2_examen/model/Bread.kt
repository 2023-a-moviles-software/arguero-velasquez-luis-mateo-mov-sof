@file:JvmName("Bread")
package epn.mov.bakery.model

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.luism.x2_examen.model.FirestormEmmiter
import java.time.LocalDate
import java.util.Date

public data class Bread(
    var name: String = "",
    var price: Double = 0.0,
    var elaborationDate: LocalDate = LocalDate.MIN,
    var isSweet: Boolean = false,
    var stock: Int = 0,
    var id: String = LocalDate.now().toString(),
    ):FirestormEmmiter
    {




    fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "price" to price,
            "elaborationDate" to elaborationDate.toString(),
            "isSweet" to isSweet,
            "stock" to stock,
            "id" to id
        )
    }

    companion object CREATOR:FirestormEmmiter.CREATOR<Bread>{
        override suspend fun createFromDocumentSnapshow(documentSnapshot: DocumentSnapshot): Bread {
            return Bread(
                documentSnapshot.getString("name")!!,
                documentSnapshot.getDouble("price")!!,
                LocalDate.parse(documentSnapshot.getString("elaborationDate")!!),
                documentSnapshot.getBoolean("isSweet")!!,
                documentSnapshot.getLong("stock")!!.toInt(),
                documentSnapshot.getString("id")!!
            )
        }

    }

        override fun add(collectionReference: CollectionReference) {
            collectionReference.document(id).set(toMap())
        }

        override fun set(collectionReference: CollectionReference) {
            collectionReference.document(id).set(toMap())
        }

        override fun delete(collectionReference: CollectionReference) {
            collectionReference.document(id).delete()
        }

        override fun toString(): String {
        return "Bread(id=$id, name='$name', price=$price, elaborationDate=$elaborationDate, isSweet=$isSweet, stock=$stock)"
    }


}