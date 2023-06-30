package com.example.moviles_soft_01

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(
    val context: Context?
):SQLiteOpenHelper(
    context,
    "instance",
    null,
    1
){
    override fun onCreate(p0: SQLiteDatabase?) {
        val srcipt = """
            CREATE TABLE TRAINER(
                id INTEGER PRIMARY KEY AUTOINCRCEMENT,
                name VARCHAR(50),
                description VARCHAR(50)
            )
        """.trimIndent()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun createTrainer(trainer:BTrainer):Boolean{
        val values = ContentValues().also {
            it.put("name",trainer.nombre)
            it.put("description",trainer.description)
        }

        val resultCode = writableDatabase.insert("trainer",null,values)
        return resultCode!=(-1).toLong();

    }

    fun deleteTrainer(id:Int):Boolean{
        val resultCode = writableDatabase.delete(
            "Trainer",
            "id=",
            arrayOf(id.toString())
        )

        return resultCode==0
    }

    fun updateTrainer(id:Int,trainer:BTrainer):Boolean{
        val values = ContentValues().also {
            it.put("name",trainer.nombre)
            it.put("description",trainer.description)
        }

        val resultCode = writableDatabase.update(
            "Trainer",
            values,
            "id=",
            arrayOf(id.toString())
        )

        return resultCode==0
    }

    fun getTrainer(id:Int):BTrainer? {
        val readableDatabase = readableDatabase;
        val queryResult = readableDatabase.rawQuery(
            "SELECT * FROM trainer WHERE ID = ?",
            arrayOf(id.toString())
        )

        if (queryResult.moveToFirst()) {
            queryResult.close()
            readableDatabase.close()
            return null;
        }

        val trainer = BTrainer(
            queryResult.getInt(0),
            queryResult.getString(1),
            queryResult.getString(2),
        )

        queryResult.close()
        readableDatabase.close()

        return trainer
    }
}