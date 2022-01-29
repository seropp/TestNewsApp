package com.example.testnewsapp.headlines_categories

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.*
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


class EverythingNewsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var everythingNewsRecyclerView: RecyclerView;
    private lateinit var list: ArrayList<NewsClass>
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchView: SearchView
    private var currentLanguage: String? = null
    private var currentSources: String? = null

    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private lateinit var swiped: SwipeRefreshLayout

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.everything_new_fragment, null)

        swiped = view.findViewById(R.id.swiped_everything)
        swiped.setOnRefreshListener(this)
        currentLanguage = loadData("LANGUAGE")
        currentSources = loadData("SOURCES")
        onRefresh()

        everythingNewsRecyclerView = view.findViewById(R.id.recycler_view_of_everything)

        searchView = view.findViewById(R.id.search_everything)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                init()
                getNews(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                init()
                getNews(query = null)
                return false
            }
        })
        init()
        getNews()
        return view
    }

    private fun init() {
        list = ArrayList()
        everythingNewsRecyclerView.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter(context, list)
        everythingNewsRecyclerView.adapter = newsAdapter
    }

    private fun getNews(query: String? = null) = lifecycleScope.launch {


        val manager = RequestManagerForNewsAPI(requireContext())
        manager.findEverythingNews(
            list = list,
            sources = currentSources,
            language = currentLanguage,
            query = query
        )
    }

    private fun loadData(key: String): String? {
        val pref: SharedPreferences =
            requireActivity().getSharedPreferences("user_settings", Context.MODE_PRIVATE)
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