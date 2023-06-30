package com.example.moviles_soft_01

import java.io.File

class BTrainer(
    var id:Int = 0,
    var nombre:String = "",
    var description:String = ""
){
    override fun toString(): String {
        return "{id:$id,nombre:$nombre,description:$description}";
    }

    companion object{
        var file = File("Stored")
    }
}