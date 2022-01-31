package com.example.testnewsapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.testnewsapp.navigation_fragments.headlines_categories.*
import com.example.testnewsapp.navigation_fragments.BookmarksFragment
import com.example.testnewsapp.navigation_fragments.headlines_categories.EverythingNewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

import com.example.testnewsapp.navigation_fragments.SettingsFragment
import com.example.testnewsapp.user_statistic.StatisticFragment
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction().add(R.id.main_container, CategoriesFragment())
            .commit()
    }

    private var navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null

            when (item.itemId) {
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

                R.id.navigation_btn_headlines -> selectedFragment = CategoriesFragment()
                R.id.navigation_btn_statistic -> selectedFragment = StatisticFragment()

                R.id.navigation_btn_everything -> selectedFragment = EverythingNewsFragment()

                R.id.navigation_btn_setting -> selectedFragment = SettingsFragment()

            }
            if (selectedFragment != null) {

                supportFragmentManager.beginTransaction().replace(
                    R.id.main_container,
                    selectedFragment!!
                ).commit()
            }
            true
        }
}



