package com.example.testnewsapp.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testnewsapp.MainActivity
import com.example.testnewsapp.R
import com.example.testnewsapp.databinding.ActivityLoginBinding
import com.example.testnewsapp.internet_connection.InternetConnection
import com.example.testnewsapp.internet_connection.NetworkManager
import com.example.testnewsapp.onboarding.OnboardingActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var auth: FirebaseUser? = FirebaseAuth.getInstance().currentUser
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
            if (NetworkManager.isNetworkAvailable(this)) {
                createUI()
            } else {
                startActivity(
                    Intent(
                        this,
                        InternetConnection::class.java
                    )
                )
            }
        }


        btnSignOut = findViewById(R.id.btn_signout)
        btnSignOut.setOnClickListener {
            AuthUI.getInstance().signOut(this).addOnSuccessListener {

                val loginTxtName: TextView = findViewById(R.id.login_txt_name)
                val loginEmail: TextView = findViewById(R.id.login_email)
                loginTxtName.text = "Username"
                loginEmail.text = "Email"
                auth = null
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

            if (auth != null) {
                if (NetworkManager.isNetworkAvailable(this)) {
                    val path = FirebaseDatabase.getInstance().getReference("users")
                        .child(auth!!.uid)
                        .child("info")
                    path.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {

                            if (snapshot.child("onboarding").value == "no") {

                                switchToOnboarding()
                            } else {
                                switchToMain()
                                getUserSettings()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
                } else {
                    startActivity(Intent(this, InternetConnection::class.java))
                }
            } else {
                switchToOnboarding()
            }
        }
    }

    private fun getUserSettings() {
        val path = FirebaseDatabase.getInstance().getReference("users")
            .child(auth!!.uid)
            .child("info")


        path.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pref: SharedPreferences =
                    getSharedPreferences("user_settings", Context.MODE_PRIVATE)

                val editor = pref.edit()
                editor.apply {
                    val l: String? = snapshot.child("default_language").value as String?
                    val r: String? = snapshot.child("default_region").value as String?
                    val s: String? = snapshot.child("default_sources").value as String?
                    putString("LANGUAGE", l)
                    putString("COUNTRY", r)
                    putString("SOURCES", s)
                }.apply()

            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun createUI() {
        auth?.let {

            val loginTxtName: TextView = findViewById(R.id.login_txt_name)
            val loginEmail: TextView = findViewById(R.id.login_email)
            loginTxtName.text = auth!!.displayName
            loginEmail.text = auth!!.email
            idCurrentUser = auth!!.uid

            users = FirebaseDatabase.getInstance().getReference("users")
            currentUserReference = users.child(auth!!.uid)
            currentUserReference
                .child("info")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (!snapshot.hasChildren()) {

                            currentUserReference.child("info").child("onboarding").setValue("no")

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })

            val userMap = mapOf(
                "email" to auth!!.email,
                "name" to auth!!.displayName,
            )
            currentUserReference.updateChildren(userMap)
            bookMarks = currentUserReference.child("bookmarks")

        }
    }

    fun switchToOnboarding() {
        startActivity(Intent(this, OnboardingActivity::class.java))
    }

    fun switchToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
