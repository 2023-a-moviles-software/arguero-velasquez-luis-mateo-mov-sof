@file:JvmName("Bakery")
package epn.mov.bakery.model

import android.util.Log
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
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap
import kotlin.jvm.Transient

public class Bakery(
    var name:String = "",
    var ruc:String = "",
    var address:String = "",
    var collectionReference:CollectionReference,
    var collection:MutableMap<String, BreadCollection> = mutableMapOf(),
):FirestormEmmiter
{



    fun getBreadsCount():Map<String,Int>{
        val ret =collection
            .mapValues{ it.value.count()}
        return ret
    }

    fun getBreads():Map<String,List<Bread>>{
        return Collections.unmodifiableMap(collection.mapValues { it.value.breads });
    }
    fun bakeBread(bread: Bread){
        var breadCol = collection[bread.name]

        if(breadCol!=null){
            bread.setParentReference(breadCol.getDocumentReference().collection("breads"))
            breadCol.breads.add(bread)
        }
        else{
            breadCol = BreadCollection(mutableListOf(bread),bread.name,getDocumentReference().collection("breads"))
            bread.setParentReference(breadCol.getDocumentReference().collection("breads"))
            collection[bread.name] = breadCol
            breadCol.add()
        }
        bread.add()

    }

    fun getByName(name:String):List<Bread>{
        return (collection[name]?.breads)?:listOf();
    }

    fun sellBread(name:String,quantity:Int):Boolean{
        val breadCol = collection[name];
        if(breadCol==null) return false

        val availableBread = breadCol.count()
        if(quantity>availableBread) return false;

        breadCol.remove(quantity)
        breadCol.set()
        return true;
    }

    fun discardOutOfStock(){
        collection.forEach { (_, u) -> u.breads.removeIf { it.stock==0 } }
    }



    fun isBreadExpired(bread: Bread, maxSellableAge: Int):Boolean{
        val todayLocalDate = LocalDate.now();
        val daysAgo = {date:LocalDate -> date.until(todayLocalDate).days}
        val expirePredicate = {date:LocalDate -> daysAgo(date)>maxSellableAge};
        return expirePredicate(bread.elaborationDate)
    }
    fun discardBreadOlderThan(maxSellableAge:Int){
        val breadExpiredPredicate = {bread: Bread ->isBreadExpired(bread,maxSellableAge)}
        collection.forEach { (_, breadList) -> breadList.breads.removeIf(breadExpiredPredicate) }
    }

    fun discardBreadNamed(breadName:String){
        collection.remove(breadName)?.delete()
    }

    fun renameBread(oldName:String, newName: String):Boolean{
        if(!collection.containsKey(oldName)) return false
        if(collection.containsKey(newName)) return false

        collection[newName] = collection.remove(oldName)!!
            .apply { delete() }
            .apply { renameBread(newName) }
            .apply { add() }
        return true
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "ruc" to ruc,
            "address" to address
        )
    }

    override fun setParentReference(collectionReference: CollectionReference){
        this.collectionReference = collectionReference
    }

    override fun getDocumentReference(): DocumentReference {
        return  this.collectionReference.document(ruc)
    }


    override fun add() {
        collectionReference.document(ruc).set(toMap())
        collection.forEach { s, breadCollection -> breadCollection.add()}
    }

    override fun set() {
        collectionReference.document(ruc).set(toMap())
        collection.forEach { s, breadCollection -> breadCollection.add()}
    }

    override fun delete() {
        collectionReference.document(ruc).delete()
        collection.forEach { s, breadCollection -> breadCollection.delete()}
    }

    override fun toString(): String {
        return "{name:$name,bread_collection:$collection}"
    }

    companion object CREATOR:FirestormEmmiter.CREATOR<Bakery>{
        override fun createFromDocumentSnapshow(documentSnapshot: DocumentSnapshot): Promise<Bakery> {
            return documentSnapshot.reference.collection("breads")
                .get()
                .toPromise()
                .promisePipe { it.documents.map(BreadCollection.CREATOR::createFromDocumentSnapshow).toBigPromise() }
                .pipe {breadColList->
                    val breadCollections = breadColList.filterNotNull()
                        .associateBy { it.id }
                        .toMutableMap()
                    Bakery(
                        documentSnapshot.getString("name")!!,
                        documentSnapshot.getString("ruc")!!,
                        documentSnapshot.getString("address")!!,
                        documentSnapshot.reference.parent,
                        breadCollections,
                    )
                }

        }

    }
}
