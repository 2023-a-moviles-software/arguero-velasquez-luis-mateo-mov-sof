package com.luism.x2_examen.util

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
    }
}