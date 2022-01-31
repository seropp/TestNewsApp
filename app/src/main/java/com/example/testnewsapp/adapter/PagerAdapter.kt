package com.example.testnewsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testnewsapp.navigation_fragments.headlines_categories.HeadlinesFragment


class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {

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

    override fun getItemCount(): Int {
        return 7
    }
}
