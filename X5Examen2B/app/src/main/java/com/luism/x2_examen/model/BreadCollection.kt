package epn.mov.bakery.model

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.luism.x2_examen.model.FirestormEmmiter
import com.luism.x2_examen.util.Infix.Companion.listen
import com.luism.x2_examen.util.Infix.Companion.pipe
import com.luism.x2_examen.util.Infix.Companion.promisePipe
import com.luism.x2_examen.util.Infix.Companion.toBigPromise
import com.luism.x2_examen.util.Infix.Companion.toPromise
import kotlinx.coroutines.tasks.await
import org.chromium.base.Promise
import java.time.LocalDate


data class BreadCollection(
    var breads: MutableList<Bread>,
    var id: String = breads.first().name,
    var collectionReference: CollectionReference
):FirestormEmmiter
{

    fun removeExpired(maxAge:Long){
        breads.removeIf {  it.elaborationDate < LocalDate.now().minusDays(maxAge) }
    }

    fun renameBread(newName:String){
        id=newName
        breads.forEach{
            it.name = newName
            it.setParentReference(getDocumentReference().collection("breads"))
        }
    }

    fun count():Int{
        return breads.map { it.stock }.stream().mapToInt { it }.sum()
    }

    fun remove(amount:Int){
        var remaining = amount

        breads.forEach{bread->
            val stock = bread.stock
            val newStock = if(stock>remaining)
                stock-remaining
            else 0;

            bread.stock = newStock
            remaining -= stock-newStock;
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf("id" to id)
    }

    override fun setParentReference(collectionReference: CollectionReference){
        this.collectionReference = collectionReference
    }

    override fun getDocumentReference(): DocumentReference{
        return  this.collectionReference.document(id)
    }

    override fun add() {
        getDocumentReference().set(toMap())
        breads.forEach{ it.add()}
    }

    override fun set() {
        getDocumentReference().set(toMap())
        breads.forEach{ it.add()}
    }

    override fun delete() {
        breads.forEach{ it.delete() }
        getDocumentReference().delete()
    }

    override fun toString(): String {
        return breads.toString()
    }

    companion object CREATOR: FirestormEmmiter.CREATOR<BreadCollection>{
        override fun createFromDocumentSnapshow(documentSnapshot: DocumentSnapshot): Promise<BreadCollection> {
            return documentSnapshot.reference
                .collection("breads")
                .get()
                .toPromise()
                .promisePipe { it.map(Bread.CREATOR::createFromDocumentSnapshow).toBigPromise()  }
                .pipe {dirtyList->
                    val breadList = dirtyList.filterNotNull().toMutableList()
                    BreadCollection(breadList,documentSnapshot.id,documentSnapshot.reference.parent)
                }
        }

    }
}