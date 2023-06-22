package com.example.moviles_soft_01

import java.io.File

class BTrainer(
    var id:Int = 0,
    var nombre:String = "",
    var description:String = ""
){

    companion object{
        var file = File("Stored")
    }
}