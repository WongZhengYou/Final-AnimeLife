package com.example.maeassignemnt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var register_link: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // reference the views from the layout
        usernameEditText = findViewById(R.id.username_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)
        register_link = findViewById(R.id.register_link)

        // set up a click listener for the login button
        loginButton.setOnClickListener {
            // get the entered username and password
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // perform login logic here, e.g. check if username and password are valid
            if (username == "user" && password == "password") {
                // login successful, show a toast message
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            } else {
                // login failed, show a toast message
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()

            }
            // set up a click listener for the register text view
            register_link.setOnClickListener {
                // start the RegisterActivity
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
        }

    }
}
}