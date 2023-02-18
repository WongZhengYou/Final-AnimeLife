package com.example.maeassignemnt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executors
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    //Firebase Auth
    private var auth: FirebaseAuth = Firebase.auth
    private var currentUser: FirebaseUser? = null

    // widgets
    private lateinit var loginButton: MaterialButton
    private lateinit var registerLink: TextView
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var txt_password: TextInputLayout
    private lateinit var txt_email: TextInputLayout

    // variables to hold values
    var regexPattern = "^(.+)@(\\S+)$"
    var emptyField = "This field cannot be empty"
    private val KEY_EMPTY = ""

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = auth.currentUser

        if (currentUser != null) {
            reload()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // reference the views from the layout
        loginButton = findViewById(R.id.login_button)
        registerLink = findViewById(R.id.register_link)
        etEmail = findViewById(R.id.et_login_email)
        etPassword = findViewById(R.id.et_login_password)
        txt_password = findViewById(R.id.txt_field_login_password)
        txt_email = findViewById(R.id.txt_field_login_email)

        loginButton.setOnClickListener {
            RunLoginTask()
        }

        registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun reload() {
        val intent = Intent(this, NavigationActivity::class.java)
        startActivity(intent)
    }

    private fun RunLoginTask() {

        // do something in background
        var email = etEmail.text.toString()
        var password = etPassword.text.toString()
        if (validateInputs(email, password)) {
            // perform login logic here, e.g. check if username and password are valid
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        currentUser = auth.currentUser
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        reload()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this, "Invalid email or password",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        txt_email.error = null
        txt_password.error = null
        if (email == KEY_EMPTY) {
            txt_email.error = emptyField
            txt_email.requestFocus()
            return false
        }
        if (!Pattern.compile(regexPattern).matcher(email).matches()) {
            txt_email.error = "Invalid email format"
            txt_email.requestFocus()
            return false
        }
        if (password == KEY_EMPTY) {
            txt_password.error = emptyField
            txt_password.requestFocus()
            return false
        }
        if (password.length < 6) {
            txt_password.error = "Password must be >= 6 characters"
            txt_password.requestFocus()
            return false
        }
        return true
    }
}