package com.example.moviles_soft_01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class BListView : AppCompatActivity() {
    val trainerArray = BBaseDatosMemoria.trainers;
    var selectedId = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)


        val listView = findViewById<ListView>(R.id.tv_list_view)

        val lvAdaptar = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            trainerArray
        )

        listView.adapter = lvAdaptar;
        lvAdaptar.notifyDataSetChanged();

        notifyListView = {lvAdaptar.notifyDataSetChanged()}
        findViewById<Button>(R.id.btn_add).setOnClickListener { addTrainer(lvAdaptar) }
        registerForContextMenu(listView)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.id_delete->{
                BBaseDatosMemoria.trainers[selectedId].nombre+="-";
                notifyListView()
                openDialog()
                return true;
            }
            R.id.id_edit->{
                BBaseDatosMemoria.trainers[selectedId].nombre+="+";
                notifyListView()
                return true;
            }
            else->super.onContextItemSelected(item)
        }

    }

    fun showDetailBread(){
        Intent()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater;
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        selectedId = info.position;
    }

    var notifyListView= {}
    fun addTrainer(arrayAdapter: ArrayAdapter<BTrainer>){
        arrayAdapter.add(BTrainer(10,"Johan","Se llama Johan"));
        arrayAdapter.notifyDataSetChanged();
    }

    fun openDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar")
        builder.setPositiveButton("Aceptar",{dialog, which->})
        builder.setNegativeButton("Cancelar",null)

        val options = resources.getStringArray(R.array.string_array_dialog_options)

        val preset = booleanArrayOf(
            true,
            false,
            false,
            false,
            false,
            false,
            false)

        builder.setMultiChoiceItems(
            options,
            preset,
            {dialog, which, isChecked -> "Dio click en $which"}
        )

        val dialog = builder.create()
            .also{it.show()}
    }
}