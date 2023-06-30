package com.example.moviles_soft_01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CIntentExplicitParameters : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicit_parameters)
        val name = intent.getStringExtra("name")
        val lastName = intent.getStringExtra("lastName")
        val age = intent.getIntExtra("age",0)
        findViewById<Button>(R.id.btn_return_ans).setOnClickListener {
            returnAnswer("{name:$name,lastName:$lastName,age:$age}")
        }
    }

    fun returnAnswer(answer:String){
        val returnIntent = Intent()
        returnIntent.putExtra("modifiedName",answer)
        setResult(RESULT_OK,returnIntent)
        finish()
    }
}