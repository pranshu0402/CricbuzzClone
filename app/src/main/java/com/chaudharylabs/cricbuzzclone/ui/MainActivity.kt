package com.chaudharylabs.cricbuzzclone.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.chaudharylabs.cricbuzzclone.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private var navHostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        supportActionBar?.hide()

        bottomNavigationView = findViewById<View>(R.id.bottom_nav_view) as BottomNavigationView
        navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)
        bottomNavigationView.itemIconTintList = null

        bottomNavigationView.inflateMenu(R.menu.home_menu_items)
        val inflater = navHostFragment?.navController?.navInflater
        val graph = inflater?.inflate(R.navigation.cric_nav_graph)
        if (graph != null) {
            navHostFragment?.navController?.graph = graph
        }
        NavigationUI.setupWithNavController(
            bottomNavigationView,
            navHostFragment?.navController!!
        )
    }
}