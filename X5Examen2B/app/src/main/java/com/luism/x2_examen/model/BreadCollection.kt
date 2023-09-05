package epn.mov.bakery.model

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.luism.x2_examen.model.FirestormEmmiter
import kotlinx.coroutines.tasks.await
import java.time.LocalDate


data class BreadCollection(
    var breads: MutableList<Bread>
):FirestormEmmiter
{
    var id: String = breads.first().name

    fun removeExpired(maxAge:Long){
        breads.removeIf {  it.elaborationDate < LocalDate.now().minusDays(maxAge) }
    }

    fun renameBread(newName:String){
        breads.forEach{ it.name = newName }
    }

    fun count():Int{
        return breads.map { it.stock }.stream().mapToInt { it }.sum()
    }

    fun toMap(): Map<String, Any> {
        return mapOf("id" to id)
    }

    override fun add(collectionReference: CollectionReference) {
        collectionReference.document(id).set(toMap())
        breads.forEach{ it.add(collectionReference.document(id).collection("breads"))}
    }

    override fun set(collectionReference: CollectionReference) {
        collectionReference.document(id).set(toMap())
        breads.forEach{ it.add(collectionReference.document(id).collection("breads"))}
    }

    override fun delete(collectionReference: CollectionReference) {
        collectionReference.document(id).set(toMap())
        breads.forEach{ it.delete(collectionReference.document(id).collection("breads")) }
    }

    override fun toString(): String {
        return breads.toString()
    }

    companion object CREATOR: FirestormEmmiter.CREATOR<BreadCollection>{
        override suspend fun createFromDocumentSnapshow(documentSnapshot: DocumentSnapshot): BreadCollection {
            val breadCollection = documentSnapshot.reference
                .collection("breads")
                .get()
                .await()
                .documents
                .map{ Bread.CREATOR.createFromDocumentSnapshow(it) }

            return BreadCollection(breadCollection.toMutableList())
        }

    }
}