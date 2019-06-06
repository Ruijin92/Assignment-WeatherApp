package com.hva.weather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.hva.weather.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        setNavigationController()
    }
    /**
     * The NavigationController handles the transition between the different Fragments by looking up the IDs
     * and searching for them in the mobile_navigation.xml
     * Means that the items in the Menu should have the same ID names as the ones in the mobile_navigation.xml
     */
    fun setNavigationController(){
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController) //direct access of the an id(bottom_nav) -> Kotlin Style
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    /**
     * The onSupportNavigateUp 
     */
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)  }
}
