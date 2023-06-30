package com.example.moviles_soft_01

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.example.moviles_soft_01.R.*

class MainActivity : AppCompatActivity() {
    inline fun <A,B> A.then(block:(A)->B):B{
        return block(this)
    }


    val callbackContentIntentExplicit = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result->
            if(result.resultCode == Activity.RESULT_OK)
            if(result.data !=null)
             "${result.data!!.getStringExtra("modifiedName")}"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)



        val btnACicloVida = findViewById<Button>(id.btn_ciclo_vida)
        val btnBListView = findViewById<Button>(id.btn_list_view)
        btnACicloVida.setOnClickListener { irActividad(AACicloVida::class.java) }
        btnBListView.setOnClickListener { irActividad(BListView::class.java) }

        val explicitBtn = findViewById<Button>(R.id.btn_explicit)
        explicitBtn.setOnClickListener {
            startActivityWithParms(CIntentExplicitParameters::class.java)
        }

        findViewById<Button>(R.id.btnDB).setOnClickListener {
            Intent(this,ECrudEntrenador::class.java)
                .then{startActivity(it)}
        }

        val implicitBtn = findViewById<Button>(R.id.btn_implicit)
        implicitBtn.setOnClickListener {
            val intentWithAnswer = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            callbackIntentPickUri.launch(intentWithAnswer)
        }
    }

    fun irActividad(clazz:Class<*>){
        val intent = Intent(this,clazz)
        startActivity(intent)
    }

    fun startActivityWithParms(clazz:Class<*>){
        val explicitIntent = Intent(this,clazz)
        explicitIntent.putExtra("name","Luis")
            .putExtra("lastName","Arguero")
            .putExtra("age",21)

        callbackContentIntentExplicit.launch(explicitIntent)
    }

    val callbackIntentPickUri =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result->
            if(result.resultCode== RESULT_OK)
                if(result.data != null){
                    val uri:Uri = result.data!!.data!!
                    val cursor = contentResolver.query(uri,null,null,null,null,null)
                    cursor?.moveToFirst()
                    val indexPhone = cursor?.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                    val telephone = cursor?.getString(indexPhone!!);
                    cursor?.close()
                    "Telephone: $telephone"
                }
        }

}