package com.luism.x2_examen.persistence

import android.content.Context
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.luism.x2_examen.util.Infix.Companion.hold
import epn.mov.bakery.model.Bakery
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.google.firebase.ktx.Firebase
import com.luism.x2_examen.util.Infix.Companion.listen
import com.luism.x2_examen.util.Infix.Companion.pipe
import com.luism.x2_examen.util.Infix.Companion.promisePipe
import com.luism.x2_examen.util.Infix.Companion.toBigPromise
import com.luism.x2_examen.util.Infix.Companion.toPromise
import org.chromium.base.Promise
import java.io.FileNotFoundException

class SingletonManager {
    companion object{
        val bakeries get() = _bakeries;

        lateinit var  reference: CollectionReference
        private val filename = "instance.json";
        private var context: Context? = null;
        private var _bakeries: MutableMap<String,Bakery> = mutableMapOf();

        fun init(context: Context):Promise<Unit>{
            reference = Firebase.firestore.collection("bakeries")

            return reference.get()
                .toPromise()
                .promisePipe { it.documents.map(Bakery.CREATOR::createFromDocumentSnapshow).toBigPromise() }
                .listen { _bakeries = it.filterNotNull().associateBy { it.ruc }.toMutableMap() }
                .pipe {}
        }




    }
}