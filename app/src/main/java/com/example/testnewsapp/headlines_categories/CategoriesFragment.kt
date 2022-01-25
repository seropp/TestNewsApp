package com.example.testnewsapp.headlines_categories

import android.os.Bundle
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.testnewsapp.R
import com.example.testnewsapp.adapter.PagerAdapter
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout


class CategoriesFragment : Fragment() {


    lateinit var tabLayout: TabLayout
    lateinit var pagerAdapter: PagerAdapter


    lateinit var viewPager: ViewPager


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.categories_fragment, null)

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