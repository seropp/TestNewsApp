package com.example.testnewsapp.internet_connection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.testnewsapp.R
import com.example.testnewsapp.login.LoginActivity
import com.example.testnewsapp.login.LoginFirebaseUI


class InternetConnection : AppCompatActivity() {

    private lateinit var retryBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internet_connection)


        retryBtn = findViewById(R.id.internet_connection_btn)
        retryBtn.setOnClickListener {

            if (NetworkManager.isNetworkAvailable(this)) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show()
            }
        }
    }
}