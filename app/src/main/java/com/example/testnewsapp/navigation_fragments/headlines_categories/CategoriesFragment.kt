package com.example.testnewsapp.navigation_fragments.headlines_categories

import android.os.Bundle
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.testnewsapp.R
import com.example.testnewsapp.adapter.PagerAdapter
import com.google.android.material.tabs.TabLayout


class CategoriesFragment : Fragment() {


    private lateinit var tabLayout: TabLayout
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var viewPager: ViewPager2


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.categories_fragment, container, false)

        tabLayout = view.findViewById(R.id.include)
        viewPager = view.findViewById(R.id.fragment_container_for_categories)

        val fm: FragmentManager = requireActivity().supportFragmentManager
        pagerAdapter = PagerAdapter(fm, lifecycle)
        viewPager.adapter = pagerAdapter

        tabLayout.addTab(tabLayout.newTab().setText("home"))
        tabLayout.addTab(tabLayout.newTab().setText("science"))
        tabLayout.addTab(tabLayout.newTab().setText("technology"))
        tabLayout.addTab(tabLayout.newTab().setText("business"))
        tabLayout.addTab(tabLayout.newTab().setText("sports"))
        tabLayout.addTab(tabLayout.newTab().setText("health"))
        tabLayout.addTab(tabLayout.newTab().setText("entertainment"))

        tabLayout.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab?.position!!
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        return view
    }
}