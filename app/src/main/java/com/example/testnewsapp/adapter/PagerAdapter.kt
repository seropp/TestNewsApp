package com.example.testnewsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.testnewsapp.categoryFragments.*
import retrofit2.http.Query


class PagerAdapter(fm: FragmentManager, behavior: Int, private var tabCount: Int = behavior) :
    FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> ScienceFragment()
            2 -> TechnologyFragment()
            3 -> BusinessFragment()
            4 -> SportsFragment()
            5 -> HealthFragment()
            6 -> EntertainmentFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }


}