package com.example.testnewsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.testnewsapp.headlines_categories.*


class PagerAdapter(fm: FragmentManager, behavior: Int, private var tabCount: Int = behavior) :
    FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {

        return when (position) {

            0 -> HeadlinesFragment("general")
            1 -> HeadlinesFragment("science")
            2 -> HeadlinesFragment("technology")
            3 -> HeadlinesFragment("business")
            4 -> HeadlinesFragment("sports")
            5 -> HeadlinesFragment("health")
            6 -> HeadlinesFragment("entertainment")

            else -> HeadlinesFragment("general")

        }
    }

    override fun getCount(): Int {
        return tabCount
    }

}