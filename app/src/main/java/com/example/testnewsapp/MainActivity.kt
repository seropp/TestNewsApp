package com.example.testnewsapp

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
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var mToolbar: Toolbar

    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)



        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        bottomNav.selectedItemId = R.id.navigation_btn_headlines

//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container_for_categories, HomeFragment()).commit()

    }

    private var navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {



//                var selectedFragment1: Fragment? = null
                var selectedFragment: Fragment? = null

                when (item.itemId) {

                    R.id.navigation_btn_headlines -> {
                        selectedFragment = CategoriesFragment()
//                        startActivity(Intent(this@MainActivity, MainActivity::class.java))

                    }
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
                }
                when {

                    selectedFragment != null -> {

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment).commit()

                    }
                    else -> {
                        Toast.makeText(
                            this@MainActivity,
                            "ALL NULL",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                return true
            }

        }



}