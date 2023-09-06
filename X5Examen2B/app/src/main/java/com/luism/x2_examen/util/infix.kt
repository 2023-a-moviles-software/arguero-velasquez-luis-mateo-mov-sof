package com.luism.x2_examen.util

import com.google.android.gms.tasks.Task
import com.luism.x2_examen.util.Infix.Companion.then
import org.chromium.base.Promise

class Infix {
    companion object{
        fun <A,B> A.hold(function:(A)->B,block:(A)->Any):B{
            val value = function(this)
            block(this);
            return value
        }
        infix fun <A,B> A.then(block:(A)->B):B{
            return block(this)
        }

        fun <E,F> Promise<E>.pipe(function:(E)->F):Promise<F>{
            val promise = Promise<F>()
            this.then(
                {e-> promise.fulfill(function(e))},
                {e ->promise.reject(e)}
            )
            return promise
        }

        fun <E> Promise<E>.listen(function:(E)->Unit):Promise<E>{
            val promise = Promise<E>()
            this.then(
                {e->
                    function(e)
                    promise.fulfill(e)
                },
                {e ->promise.reject(e)}
            )
            return promise
        }

        fun <E,F> Promise<E>.promisePipe(function:(E)->Promise<F>):Promise<F>{
            val promise = Promise<F>()
            this.then(
                {e->
                    function(e).then(
                        {f-> promise.fulfill(f)}
                        ,{ex ->promise.reject(ex)})

                },
                {e ->promise.reject(e)}
            )
            return promise
        }

        fun <E> List<Promise<E>>.toBigPromise():Promise<List<E?>>{
            var remaining = this.size
            val solvedValues = ArrayList<E?>(remaining)
            val bigPromise = Promise<List<E?>>()

            this.forEachIndexed { idx, promise ->
                solvedValues.add(null)
                promise.then(
                { v ->
                    solvedValues[idx] = v
                    remaining -= 1
                    if(remaining <= 0) bigPromise.fulfill(solvedValues)
                },{e->
                    remaining -= 1
                    if(remaining <= 0) bigPromise.fulfill(solvedValues)
                }
                )
            }

            return bigPromise
        }

        fun <E> Task<E>.toPromise():Promise<E>{
            val promise = Promise<E>()
            addOnSuccessListener { promise.fulfill(it) }
            addOnFailureListener { promise.reject(it) }

            return promise
        }

    }
}