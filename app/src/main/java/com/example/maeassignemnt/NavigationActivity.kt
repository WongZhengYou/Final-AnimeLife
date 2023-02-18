package com.example.maeassignemnt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class NavigationActivity : AppCompatActivity() {

    lateinit var navController: NavController

    // Firebase Authentication
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var userId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        // set up firebase auth
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            currentUser = mAuth.currentUser!!
        } else {
            // if user is null, go to login
            reload()
        }

        // Bottom Navigation
        // get navigation host fragment, instantiate navController using NavHostFragment
        navController = this.findNavController(R.id.nav_host_fragment_activity_main)

        // find reference to bottom navigation view
        val navView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        // Hook your navigation controller to bottom navigation view
        navView.setupWithNavController(navController)
    }

    private fun reload(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}