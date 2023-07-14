package com.luism.x2_examen

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.luism.x2_examen.R.layout
import com.luism.x2_examen.persistence.SingletonManager
import com.luism.x2_examen.util.Infix.Companion.then
import epn.mov.bakery.model.Bakery

class Inventory : AppCompatActivity() {
    private var selectedIndex:Int = 0
    private var bakery:Bakery? = null
    private var breadAdapter:BreadAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_inventory)

        val bakeryName = intent.getStringExtra("bakeryName")

        bakery = SingletonManager.bakeries[bakeryName]!!
        breadAdapter = BreadAdapter(this,bakery!!)

        val breadListView = findViewById<ListView>(R.id.lv_breads)
        breadListView.adapter = breadAdapter
        registerForContextMenu(breadListView)

        val fabAddBread = findViewById<FloatingActionButton>(R.id.fab_add)
        R.id.fab_add.then<Int,FloatingActionButton>(::findViewById).setOnClickListener{
            Intent(this,NewBread::class.java)
                .putExtra("bakeryName",bakeryName)
                .then { startActivity(it) }
        }

        R.id.lv_breads.then<Int,ListView>(::findViewById)
            .also{it.setOnItemClickListener(::onItemClickOfBreadListView)}

    }

    private fun onItemClickOfBreadListView(parent: AdapterView<*>, view:View, pos:Int, id:Long)
        = startBreadBatch(breadAdapter!!.keys[pos])


    private fun startBreadBatch(breadName: String){
        Intent(this,Details::class.java)
            .putExtra("breadName",breadName)
            .putExtra("bakeryName",bakery!!.name)
            .then { startActivity(it) }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        super.onContextItemSelected(item)
        val breadName = breadAdapter!!.keys[selectedIndex];
        when(item.itemId) {
            R.id.i_edit->{changeBreadNameThroughDialog(breadName)}
            R.id.i_view->{startBreadBatch(breadName)}
            R.id.i_delete->{bakery!!.discardBreadNamed(breadName)}
            R.id.i_sell->{sellBreadThroughDialog(breadName)}
            R.id.i_discard_date->{discardAgedBreadThroughDialog(breadName)}
            else->{return false}
        }
        breadAdapter!!.notifyDataSetChanged()
        return false
    }

    override fun onResume() {
        super.onResume()
        breadAdapter!!.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        SingletonManager.save()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_sell_discard, menu)
        selectedIndex = (menuInfo as AdapterView.AdapterContextMenuInfo).position
    }

    fun changeBreadNameThroughDialog(targetBreadName: String){
        val editText = EditText(this)

        AlertDialog.Builder(this)
            .setTitle("Ingrese un nuevo nombre")
            .setNegativeButton("Cancelar",null)
            .setPositiveButton("Aceptar") { i, input ->
                bakery!!.renameBread(targetBreadName, editText.text.toString())
                SingletonManager.save()
                breadAdapter!!.notifyDataSetChanged()
            }
            .setView(editText)
            .create()
            .show()
    }

    fun sellBreadThroughDialog(targetBreadName: String){
        val editText = EditText(this)
            .also { it.inputType=InputType.TYPE_CLASS_NUMBER }

        AlertDialog.Builder(this)
            .setTitle("Ingrese la cantidad de pan a vender")
            .setNegativeButton("Cancelar",null)
            .setPositiveButton("Aceptar") { i, input ->
                bakery!!.sellBread(
                    targetBreadName,
                    editText.text.toString().toInt()
                )
            }
            .setView(editText)
            .create()
            .show()
    }

    fun discardAgedBreadThroughDialog(targetBreadName: String){
        val editText = EditText(this)
            .also { it.inputType=InputType.TYPE_CLASS_NUMBER }

        AlertDialog.Builder(this)
            .setTitle("Ingrese la edad máxima que el pan puede tener")
            .setNegativeButton("Cancelar",null)
            .setPositiveButton("Aceptar") { i, input ->
                val breadMaxAge = editText.text.toString().toInt()
                bakery!!.getBreads()[targetBreadName]!!
                    .removeIf(Bakery.getIsBreadExpiredPredicate(breadMaxAge))
            }
            .setView(editText)
            .create()
            .show()
    }
}