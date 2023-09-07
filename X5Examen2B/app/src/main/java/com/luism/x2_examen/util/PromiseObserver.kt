package com.luism.x2_examen.util

import com.luism.x2_examen.util.Infix.Companion.then
import org.chromium.base.Promise
import java.lang.Exception

class PromiseObserver<E>(val promise: Promise<E>,val errorCallback: (Exception)->Unit){
    private var callbacks: MutableList<(E)->Unit>? = mutableListOf()

    init {
        promise.then({
                e->
            callbacks!!.forEach { f -> f(e) }
            callbacks!!.clear()
            callbacks = null
        },{
            errorCallback(it)
        })
    }
    fun then(function:(E)->Unit){
        if(promise.isRejected || promise.isFulfilled){
            function(promise.result)
            return
        }
        callbacks!!.add (function )
    }
}