package com.example.maeassignemnt

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var logoImage: ImageView
    private var loadingTime = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)

        // reference the views from the layout
        logoImage = findViewById(R.id.logo_image);

        // Splash Screen
        Handler().postDelayed({ // transition from one activity to another
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, loadingTime.toLong())

    }


}