package com.luism.x2_examen.model

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import org.chromium.base.Promise

interface FirestormEmmiter {
    fun setParentReference(collectionReference: CollectionReference)

    fun getDocumentReference():DocumentReference
    fun add()
    fun set()
    fun delete()

    companion object interface CREATOR<T>{
        fun createFromDocumentSnapshow(documentSnapshot: DocumentSnapshot):Promise<T>
    }
}