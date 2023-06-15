package com.example.moviles_soft_01

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.moviles_soft_01.databinding.ActivityAacicloVidaBinding

class AACicloVida : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAacicloVidaBinding
    private var globalSnackbarMsg: String = "";

    fun showSnackbar(text:String){
        globalSnackbarMsg = "$text, $globalSnackbarMsg";
        Snackbar.make(binding.root,globalSnackbarMsg,Snackbar.LENGTH_LONG)
            .setAction("Action",null)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAacicloVidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_aaciclo_vida)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        showSnackbar("onCreate")
    }

    override fun onStart() {
        super.onStart()
        showSnackbar("onStart")
    }

    override fun onResume() {
        super.onResume()
        showSnackbar("onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        showSnackbar("onDestroy")
    }

    override fun onStop() {
        super.onStop()
        showSnackbar("onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("globalSnackbarMsg",globalSnackbarMsg)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            globalSnackbarMsg = getString("globalSnackbarMsg")?:""
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_aaciclo_vida)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}