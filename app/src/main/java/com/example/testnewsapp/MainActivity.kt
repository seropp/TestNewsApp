package com.example.testnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.testnewsapp.adapter.PagerAdapter
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout

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

        categorySwipe()
    }

    private fun categorySwipe() {
        val viewPager: ViewPager = findViewById(R.id.fragment_container)
        pagerAdapter = PagerAdapter(supportFragmentManager, 7)
        viewPager.adapter = pagerAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
                if (tab.position == 0 || tab.position == 1 || tab.position == 2 || tab.position == 3 ||
                    tab.position == 4 || tab.position == 5 || tab.position == 6
                ) {
                    pagerAdapter.notifyDataSetChanged()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }
}