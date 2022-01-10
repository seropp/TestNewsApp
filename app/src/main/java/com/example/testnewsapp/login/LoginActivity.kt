package com.example.testnewsapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.testnewsapp.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.view.View
import com.example.testnewsapp.MainActivity


class LoginActivity : AppCompatActivity() {


    private val rqCode:Int =  1710
    private lateinit var providers: List<AuthUI.IdpConfig>
    private lateinit var btnSignOut: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSignOut = findViewById(R.id.btn_sing_out)
        btnSignOut.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                AuthUI.getInstance()
                    .signOut(this@LoginActivity)
                    .addOnCanceledListener {object : OnCompleteListener<Unit> {
                        override fun onComplete(p0: Task<Unit>) {
                            btnSignOut.isEnabled = false
                            showSingOptions()
                        }

                    } }.addOnFailureListener(object : OnFailureListener{
                        override fun onFailure(p0: Exception) {
                            Toast.makeText(this@LoginActivity, ""+p0.message.toString(), Toast.LENGTH_LONG).show()
                        }

                    })
            }


        })



        providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.TwitterBuilder().build())

        showSingOptions()
    }

    private fun showSingOptions() {
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), rqCode
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode ==  rqCode) {
            val response: IdpResponse? = IdpResponse.fromResultIntent(data)
            if(resultCode == requestCode) {
                val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this, "" + user?.email, Toast.LENGTH_LONG).show()
                btnSignOut.isEnabled = true
                switchAct()
            } else Toast.makeText(this, ""+ response?.error!!.message.toString(), Toast.LENGTH_LONG).show()
        }
    }


    private fun switchAct() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}