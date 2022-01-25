package com.example.testnewsapp

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.testnewsapp.headlines_categories.*
import com.example.testnewsapp.navigation_fragments.BookmarksFragment
import com.example.testnewsapp.headlines_categories.EverythingNewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.content.SharedPreferences
import com.example.testnewsapp.navigation_fragments.SettingsFragment


class MainActivity : AppCompatActivity() {

    lateinit var mToolbar: Toolbar

    lateinit var bottomNav: BottomNavigationView
    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)



        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        bottomNav.selectedItemId = R.id.navigation_btn_headlines


    }

    private var navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {


                var selectedFragment: Fragment? = null

                when (item.itemId) {

                    R.id.navigation_btn_headlines -> selectedFragment = CategoriesFragment()

                    R.id.navigation_btn_bookmarks -> {
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            selectedFragment = BookmarksFragment()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Bookmarks are available only to authorized users",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    R.id.navigation_btn_everything -> selectedFragment = EverythingNewsFragment()

                    R.id.navigation_btn_setting -> selectedFragment = SettingsFragment()

                }
                if (selectedFragment != null) {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment).commit()

                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "ALL NULL",
                        Toast.LENGTH_LONG
                    ).show()
                }

                return true
            }

        }
}



