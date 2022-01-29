package com.example.testnewsapp.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testnewsapp.MainActivity
import com.example.testnewsapp.R
import com.example.testnewsapp.databinding.ActivityLoginBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance().currentUser
    private lateinit var btnSignOut: Button
    private lateinit var btnLogIn: Button
    private lateinit var btnBeginTo: Button
    private lateinit var users: DatabaseReference
    private lateinit var currentUserReference: DatabaseReference
    private lateinit var bookMarks: DatabaseReference


    companion object {
        var idCurrentUser: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        if (auth != null) {
            createUI()
        }

        btnSignOut = findViewById(R.id.btn_signout)
        btnSignOut.setOnClickListener {
            AuthUI.getInstance().signOut(this).addOnSuccessListener {
                val loginTxtName: TextView = findViewById(R.id.login_txt_name)
                val loginEmail: TextView = findViewById(R.id.login_email)
                loginTxtName.text = "Username"
                loginEmail.text = "Email"
                idCurrentUser = null
                Toast.makeText(this, "Success logged Out", Toast.LENGTH_LONG).show()
            }
        }

        btnLogIn = findViewById(R.id.btn_log_in)
        btnLogIn.setOnClickListener {
            startActivity(Intent(this, LoginFirebaseUI::class.java))
        }

        btnBeginTo = findViewById(R.id.btn_to_begin_login)
        btnBeginTo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun createUI() {
        auth?.let {

            val loginTxtName: TextView = findViewById(R.id.login_txt_name)
            val loginEmail: TextView = findViewById(R.id.login_email)
            loginTxtName.text = auth.displayName
            loginEmail.text = auth.email
            idCurrentUser = auth.uid

            users = FirebaseDatabase.getInstance().getReference("users")
            currentUserReference = users.child(auth.uid)

            val userMap = mapOf(
                "email" to auth.email,
                "name" to auth.displayName,
            )
            currentUserReference.updateChildren(userMap)
            bookMarks = currentUserReference.child("bookmarks")
        }
    }
}
