package com.example.testnewsapp.headlines_categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import android.util.Log
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testnewsapp.GetCurrentData
import com.example.testnewsapp.R
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.models.NewsClass
import com.example.testnewsapp.api.RequestManagerForNewsAPI
import com.example.testnewsapp.bookmarks.WorkWithBookmarks
import com.example.testnewsapp.internet_connection.InternetConnection
import com.example.testnewsapp.internet_connection.NetworkManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
        currentCountry = GetCurrentData().getCurrentRegion(requireContext())
        list = ArrayList()


        swiped = v.findViewById(R.id.swiped_headlines)
        swiped.setOnRefreshListener(this)


        searchView = v.findViewById(R.id.search_for_headlines)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                init()
                getNews(query = query, adapter = newsAdapter)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                init()
                getNews(query = null, adapter = newsAdapter)
                return false
            }
        })

        init()
        getNews(query = null, adapter = newsAdapter)

        return v
    }

    private fun init() {
        recyclerView = v.findViewById(R.id.recycler_view_of_headlines)
        registerForContextMenu(recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        newsAdapter = NewsAdapter(requireContext(), list)
        recyclerView.adapter = newsAdapter
    }

    private fun getNews(query: String? = null, adapter: NewsAdapter) {


        val manager = RequestManagerForNewsAPI(requireContext())
        manager.findHeadlinesNews(
            category = category,
            list = list,
            query = query,
            country = currentCountry,
            adapter = adapter
        )

    }


    override fun onRefresh() {

        swiped.isRefreshing = false
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            131 -> {
                Log.e("TAG", "head lines ")

                if (user != null) {
                    if (NetworkManager.isNetworkAvailable(requireContext())) {

                        WorkWithBookmarks().addToBookmarks(item.groupId, newsAdapter)
                        Toast.makeText(requireContext(), "Bookmark added", Toast.LENGTH_SHORT)
                            .show()

                    } else {
                        requireContext().startActivity(Intent(requireContext(), InternetConnection::class.java))
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Bookmarks are available only to authorized users",
                        Toast.LENGTH_LONG
                    ).show()
                }
                Toast.makeText(requireContext(), "HEAD", Toast.LENGTH_SHORT).show()

                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}