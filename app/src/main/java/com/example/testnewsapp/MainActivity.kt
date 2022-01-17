package com.example.testnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.FrameStats
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.testnewsapp.adapter.PagerAdapter
import com.example.testnewsapp.bookmarks.WorkWithBookmarks
import com.example.testnewsapp.categoryFragments.*
import com.example.testnewsapp.navigation_fragments.BookmarksFragment
import com.example.testnewsapp.navigation_fragments.EverythingNewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    lateinit var mToolbar: Toolbar

    var mHome: TabItem? = null
    var mScience: TabItem? = null
    var mTechnology: TabItem? = null
    var mBusiness: TabItem? = null
    var mSports: TabItem? = null
    var mHealth: TabItem? = null
    var mEntertainment: TabItem? = null

    lateinit var tabLayout: TabLayout
    lateinit var pagerAdapter: PagerAdapter

    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)

        mHome = findViewById(R.id.home)
        mScience = findViewById(R.id.science)
        mTechnology = findViewById(R.id.technology)
        mBusiness = findViewById(R.id.business)
        mSports = findViewById(R.id.sports)
        mHealth = findViewById(R.id.health)
        mEntertainment = findViewById(R.id.entertainment)

        tabLayout = findViewById(R.id.include)

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_for_categories, HomeFragment()).commit()
        categorySwipe()
    }

    private var navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                val viewPager: ViewPager = findViewById(R.id.fragment_container_for_categories)

                var selectedFragment1: Fragment? = null
                var selectedFragment2: Fragment? = null

                when (item.itemId) {

                    R.id.navigation_btn_headlines -> {
                        selectedFragment1 = HomeFragment()

                    }
                    R.id.navigation_btn_bookmarks -> {
                        if (user != null) {
                            selectedFragment2 = BookmarksFragment()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Bookmarks are available only to authorized users",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    R.id.navigation_btn_everything -> selectedFragment2 = EverythingNewsFragment()
                }
                if (selectedFragment1 != null) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_for_categories, selectedFragment1).commit()
                } else if (selectedFragment2 != null) {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_for_navigation, selectedFragment2).commit()
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

    private fun categorySwipe() {
        val viewPager: ViewPager = findViewById(R.id.fragment_container_for_categories)
        pagerAdapter = PagerAdapter(supportFragmentManager, 7)
        viewPager.adapter = pagerAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position

                if (tab.position == 0 || tab.position == 1 || tab.position == 2 || tab.position == 3 ||
                    tab.position == 4 || tab.position == 5 || tab.position == 6
                ) {
                    pagerAdapter.notifyDataSetChanged()
                }
            }

        })
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }
}