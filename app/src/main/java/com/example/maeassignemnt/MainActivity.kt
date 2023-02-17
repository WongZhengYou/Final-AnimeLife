package com.example.maeassignemnt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var register_link: TextView
    private lateinit var auth: FirebaseAuth;
    private lateinit var customToken:
    // ...
    // Initialize Firebase Auth
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

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
            customToken?.let {
                auth.signInWithCustomToken(it)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success")
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }
                    }
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