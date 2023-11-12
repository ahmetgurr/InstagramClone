package com.example.instagramclone.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.instagramclone.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize Firebase Aut h
        auth = Firebase.auth

        val currentUser = auth.currentUser
        if(currentUser != null) { // If user is logged in, go to FeedActivity
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    fun signInClicked(view: View) {

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Enter email and password!", Toast.LENGTH_LONG).show()
        }else{
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                val intent = Intent(this, FeedActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener { exception ->
                Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signUpClicked(view: View) {
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")) {
            // If email or password is empty, show error message
            Toast.makeText(this, "Email and password are required!", Toast.LENGTH_LONG).show()
        } else {
            // If email and password are not empty, try to login
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                // If login is successful, go to FeedActivity
                val intent = Intent(this, FeedActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener { exception ->
                // If login is not successful, show error message
                Toast.makeText(this@MainActivity, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }


}