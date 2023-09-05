package com.luism.x2_examen

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.luism.x2_examen.databinding.ActivityMainBinding
import com.luism.x2_examen.persistence.SingletonManager
import com.luism.x2_examen.util.Infix.Companion.then

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var bakeryAdapter: BakeryAdapter;
    private var selectedIndex = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SingletonManager.init(applicationContext)
        bakeryAdapter = BakeryAdapter(this,SingletonManager.bakeries)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.toolbar.title = "Bienvenido al gestor de Panaderías"
        setContentView(binding.root)


//        setSupportActionBar(binding.toolbar)

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration))

        R.id.lv_bakeries.then { findViewById<ListView>(it) }
            .also { it.adapter = bakeryAdapter }
            .also { it.setOnItemClickListener(::onItemClickOfBreadListView)}
            .also { registerForContextMenu(it) }

        R.id.fab_new_bakery.then { findViewById<FloatingActionButton>(it) }
            .setOnClickListener{
                Intent(this,NewBakery::class.java).then(::startActivity)
            }
    }

    private fun onItemClickOfBreadListView(parent: AdapterView<*>, view: View, pos:Int, id:Long)
            = startBreadInventory(bakeryAdapter!!.keys[pos])


    private fun startBreadInventory(bakeryName: String){
        Intent(this,Inventory::class.java)
            .putExtra("bakeryName",bakeryName)
            .then { startActivity(it) }
    }

    private fun editBakery(bakeryName: String){
        Intent(this,NewBakery::class.java)
            .putExtra("bakeryName",bakeryName)
            .then { startActivity(it) }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        super.onContextItemSelected(item)
        val bakeryName = bakeryAdapter.keys[selectedIndex] as String;
        when(item.itemId) {
            R.id.i_edit->{editBakery(bakeryName)}
            R.id.i_view->{startBreadInventory(bakeryName)}
            R.id.i_delete->{SingletonManager.bakeries.remove((bakeryName))}
            else->return false
        }
        bakeryAdapter!!.notifyDataSetChanged()
        return false
    }

    override fun onResume() {
        super.onResume()
        bakeryAdapter!!.notifyDataSetChanged()
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
        menuInflater.inflate(R.menu.menu_main, menu)
        selectedIndex = (menuInfo as AdapterView.AdapterContextMenuInfo).position
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.i_delete -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}