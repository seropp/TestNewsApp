package com.example.testnewsapp

import android.os.Bundle
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.testnewsapp.adapter.PagerAdapter
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout


class TestFragment : Fragment() {



    lateinit var tabLayout: TabLayout
    lateinit var pagerAdapter: PagerAdapter



    var mHome: TabItem? = null
    var mScience: TabItem? = null
    var mTechnology: TabItem? = null
    var mBusiness: TabItem? = null
    var mSports: TabItem? = null
    var mHealth: TabItem? = null
    var mEntertainment: TabItem? = null
    lateinit var viewPager: ViewPager


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.test_fragment, container, false)

        mHome = view.findViewById(R.id.home)
        mScience = view.findViewById(R.id.science)
        mTechnology = view.findViewById(R.id.technology)
        mBusiness = view.findViewById(R.id.business)
        mSports = view.findViewById(R.id.sports)
        mHealth = view.findViewById(R.id.health)
        mEntertainment = view.findViewById(R.id.entertainment)

        tabLayout = view.findViewById(R.id.include)

       viewPager = view.findViewById(R.id.fragment_container_for_categories)
        categorySwipe()
        return view
    }

    private fun categorySwipe() {

        pagerAdapter = PagerAdapter(parentFragmentManager, 7)
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