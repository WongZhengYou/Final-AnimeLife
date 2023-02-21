package com.example.maeassignemnt

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    //Firebase
    private var auth: FirebaseAuth = Firebase.auth
    private var currentUser: FirebaseUser? = null
    // Firestore
    private lateinit var firestore: FirebaseFirestore
    private lateinit var docRef: DocumentReference

    // Storage
    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_profile, container, false)
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = auth.currentUser
        if(currentUser == null){
            reload()
        }
        // set up firestore
        firestore = FirebaseFirestore.getInstance()
        docRef = firestore!!.collection("users").document(currentUser?.uid!!)

        // set up firebase storage
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()
        storageReference.child("users/" + currentUser!!.uid + "/profile.jpg")

        if(currentUser != null) {
            setUpTextView()
        }

        /*profile_image.setOnClickListener{
            selectImageFromGallery()
        }*/

        /*btn_save.setOnClickListener(View.OnClickListener {
            updateFirebase()
        })*/

        /*btn_logOut.setOnClickListener(View.OnClickListener {
            logOut()
        })*/
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {

            }
    }

    // upload image
    private fun selectImageFromGallery() {
        //open gallery
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(intent, 1000)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = data!!.getData()
                uploadToFirebase(imageUri!!)
            }
        }
    }
    private fun uploadToFirebase(imageUri: Uri) {
        // upload image to Firebase storage
        val fileRef = storageReference.child("users/" + currentUser!!.uid + "/profile.jpg")
        fileRef.putFile(imageUri).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                val changeRequest = UserProfileChangeRequest.Builder()
                    .setPhotoUri(uri)
                    .build()
                currentUser!!.updateProfile(changeRequest).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Image Uploaded", Toast.LENGTH_SHORT).show()
                        val photoUrl = currentUser!!.photoUrl
                        //Picasso.get().load(photoUrl).into(profile_image)
                    }
                }
            }
        }.addOnFailureListener { e ->
            Toast.makeText(context, "Error! " + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    // get data
    private fun setUpTextView() {

        var docRefUsers: DocumentReference = firestore.collection("users").document(currentUser!!.uid)
        docRefUsers.get().addOnSuccessListener { document ->
            //id = document.get("nric").toString()
            //tv_profile_id.text = id
        }
        var photoUrl = currentUser?.photoUrl
        if(photoUrl!=null){

            //Picasso.get().load(photoUrl).into(profile_image)
        }
    }

    // save data
    private fun updateFirebase() {
        /*if (validateInput()!!) {
            val name = et_full_name.getText().toString().trim { it <= ' ' }
            val email = et_mail.getText().toString().trim { it <= ' ' }
            val nric = et_NRIC.getText().toString().trim { it <= ' ' }
            val changeRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            currentUser!!.updateProfile(changeRequest)
            val userDetails: MutableMap<String, Any> = HashMap()

            userDetails["emailAdd"] = email
            docRef.update(userDetails).addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Profile Updated",
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnFailureListener { e ->
                Toast.makeText(
                    context,
                    "Error! " + e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }*/
    }
    private fun logOut(){
        auth.signOut()
        reload()
    }

    private fun reload() {
        val intent = Intent(activity, LoginActivity::class.java )
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        setUpTextView()
    }

    override fun onStart() {
        super.onStart()
        setUpTextView()
    }
}