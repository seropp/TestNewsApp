package com.example.testnewsapp.headlines_categories


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testnewsapp.GetCurrentData
import com.example.testnewsapp.R
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.models.NewsClass
import com.example.testnewsapp.api.RequestManagerForNewsAPI
import com.example.testnewsapp.bookmarks.WorkWithBookmarks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

        list = ArrayList()

        swiped = view.findViewById(R.id.swiped_everything)
        swiped.setOnRefreshListener(this)
        currentLanguage = GetCurrentData().getCurrentLanguage(requireContext())
        currentSources = GetCurrentData().getCurrentSources(requireContext())


        everythingNewsRecyclerView = view.findViewById(R.id.recycler_view_of_everything)

        searchView = view.findViewById(R.id.search_everything)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                init()
                getNews(query, adapter = newsAdapter)
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


        return view
    }

    private fun init() {
        everythingNewsRecyclerView.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter(context, list)
        everythingNewsRecyclerView.adapter = newsAdapter
    }

    private fun getNews(query: String? = null, adapter: NewsAdapter) {
        val manager = RequestManagerForNewsAPI(requireContext())
        manager.findEverythingNews(
            list = list,
            sources = currentSources,
            language = currentLanguage,
            query = query,
            adapter = adapter
        )
    }


    override fun onRefresh() {

        swiped.isRefreshing = false
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            131 -> {
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