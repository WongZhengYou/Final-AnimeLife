package com.example.maeassignemnt

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    //Firebase
    private var auth: FirebaseAuth = Firebase.auth
    private var currentUser: FirebaseUser? = null
    private var db = Firebase.firestore

    // widgets
    private lateinit var registerButton: MaterialButton
    private lateinit var loginLink: TextView
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
        setContentView(R.layout.activity_register)

        // Find views
        loginLink = findViewById(R.id.login_link)
        etEmail = findViewById(R.id.et_registerEmail)
        etPassword = findViewById(R.id.et_registerPassword)
        txt_password = findViewById(R.id.txt_registerPassword)
        txt_email = findViewById(R.id.txt_registerEmail)
        registerButton = findViewById(R.id.register_button)

        // Set click listener for register button
        registerButton.setOnClickListener {
            registerNewUser()
        }

        loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun reload() {
        val intent = Intent(this, NavigationActivity::class.java)
        startActivity(intent)
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

    private fun registerNewUser() {

        // get data from edit text
        val password: String = etPassword.text.toString().trim()
        val email: String = etEmail.text.toString().trim()

        if (validateInputs(email,password)) {
            // create user
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(applicationContext, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                // set current user
                currentUser = auth.getCurrentUser()
                val userId: String = currentUser!!.getUid()
                // set username
                val profileChangeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(null)
                    .setPhotoUri(null)
                    .build()
                currentUser!!.updateProfile(profileChangeRequest)

                // create new document for new user
                val documentReference: DocumentReference = db.collection("users").document(userId)

                val userDetails: MutableMap<String, Any> = HashMap()
                userDetails["username"] = ""
                userDetails["email"] = email
                userDetails["profileImage"] = ""
                documentReference.set(userDetails).addOnSuccessListener {
                    Toast.makeText(
                        applicationContext,
                        "Sign Up successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    reload()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    applicationContext,
                    "Sign Up Failed! Please try again!" + e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}