package com.example.testnewsapp.headlines_categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testnewsapp.R
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.models.NewsClass
import com.example.testnewsapp.RequestManagerForNewsAPI
import com.example.testnewsapp.bookmarks.WorkWithBookmarks
import com.example.testnewsapp.navigation_fragments.BookmarksFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import java.math.BigInteger
import kotlin.collections.ArrayList


class HeadlinesFragment(var category: String) : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var headlinesRecyclerView: RecyclerView
    private lateinit var list: ArrayList<NewsClass>
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchView: SearchView
    private lateinit var currentCountry: String

    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private lateinit var swiped: SwipeRefreshLayout

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.headlines_fragment, null)
        currentCountry = loadData("COUNTRY")


        swiped = view.findViewById(R.id.swiped_headlines)
        swiped.setOnRefreshListener(this)


        headlinesRecyclerView = view.findViewById(R.id.recycler_view_of_headlines)
        searchView = view.findViewById(R.id.search_for_headlines)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                init()
                val manager = RequestManagerForNewsAPI(requireContext(), adapter = newsAdapter)
                manager.findHeadlinesNews(
                    category = category,
                    list = list,
                    query = query,
                    country = currentCountry
                )

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        init()

        val manager = RequestManagerForNewsAPI(requireContext(), adapter = newsAdapter)
        manager.findHeadlinesNews(
            category = category,
            list = list,
            query = null,
            country = currentCountry
        )

        return view
    }

    private fun init() {

        list = ArrayList()
        headlinesRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        newsAdapter = NewsAdapter(requireActivity(), list)
        headlinesRecyclerView.adapter = newsAdapter

    }


    override fun onContextItemSelected(item: MenuItem): Boolean {



            return when (item.itemId) {
                101 -> {
                    if (user != null) {
//                        WorkWithBookmarks().addToBookmarks(item.groupId, newsAdapter)
                        WorkWithBookmarks().addToBookmarks(item.groupId, newsAdapter)
                        Toast.makeText(requireContext(), "Bookmark added", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Bookmarks are available only to authorized users",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    true
                }
                else -> super.onContextItemSelected(item)
            }

    }


    private fun loadData(key: String): String {
        val pref: SharedPreferences =
            requireActivity().getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        return pref.getString(key, "ru")!!
    }

    override fun onRefresh() {
        init()

        val manager = RequestManagerForNewsAPI(requireContext(), adapter = newsAdapter)
        manager.findHeadlinesNews(
            category = category,
            list = list,
            query = null,
            country = currentCountry
        )
        swiped.isRefreshing = false
    }
}