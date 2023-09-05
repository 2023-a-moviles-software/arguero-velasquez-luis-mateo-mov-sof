package com.luism.x2_examen.model

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot

interface FirestormEmmiter {

    fun add(collectionReference: CollectionReference)
    fun set(collectionReference: CollectionReference)
    fun delete(collectionReference: CollectionReference)

    companion object interface CREATOR<T>{
        suspend fun createFromDocumentSnapshow(documentSnapshot: DocumentSnapshot):T
    }
}