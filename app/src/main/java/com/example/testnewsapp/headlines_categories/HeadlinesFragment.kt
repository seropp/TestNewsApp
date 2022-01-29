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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testnewsapp.R
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.models.NewsClass
import com.example.testnewsapp.api.RequestManagerForNewsAPI
import com.example.testnewsapp.bookmarks.WorkWithBookmarks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class HeadlinesFragment(var category: String) : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var v: View

    private lateinit var recyclerView: RecyclerView
    private lateinit var list: ArrayList<NewsClass>
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchView: SearchView
    private var currentCountry: String? = null

    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private lateinit var swiped: SwipeRefreshLayout

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = LayoutInflater.from(context).inflate(R.layout.headlines_fragment, container, false)
        currentCountry = loadData("COUNTRY")
        list = ArrayList()


        swiped = v.findViewById(R.id.swiped_headlines)
        swiped.setOnRefreshListener(this)


        searchView = v.findViewById(R.id.search_for_headlines)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                getNews(query = query)
                init()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getNews(query = null)
                init()

                return false
            }
        })
        getNews()
        init()

        return v
    }

    private fun init() {
        recyclerView = v.findViewById(R.id.recycler_view_of_headlines)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        newsAdapter = NewsAdapter(requireContext(), list)
        recyclerView.adapter = newsAdapter
    }

    private fun getNews(query: String? = null) = lifecycleScope.launch {

        val manager = RequestManagerForNewsAPI(requireContext())
        manager.findHeadlinesNews(
            category = category,
            list = list,
            query = query,
            country = currentCountry
        )
    }

    private fun loadData(key: String): String? {
        val pref: SharedPreferences =
            requireContext().getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        return pref.getString(key, null)
    }

    override fun onRefresh() {

        swiped.isRefreshing = false
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            101 -> {
                if (user != null) {
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
}