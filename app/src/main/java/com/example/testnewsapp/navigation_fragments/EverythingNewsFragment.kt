package com.example.testnewsapp.navigation_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testnewsapp.R
import com.example.testnewsapp.RequestManagerForNewsAPI
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.bookmarks.WorkWithBookmarks
import com.example.testnewsapp.models.NewsClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class EverythingNewsFragment() : Fragment() {

    private lateinit var everythingNewsArrayList: ArrayList<NewsClass>
    private lateinit var recyclerViewFromEverythingNews: RecyclerView;
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchView: SearchView

    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.everything_new_fragment, null)

        recyclerViewFromEverythingNews = view.findViewById(R.id.recycler_view_of_everythig)

        searchView = view.findViewById(R.id.search_everything)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                init()
                val manager = RequestManagerForNewsAPI()
                manager.findEverythingNews(context, everythingNewsArrayList, newsAdapter, query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })


        init()

        val manager = RequestManagerForNewsAPI()
        manager.findEverythingNews(context, everythingNewsArrayList, newsAdapter)
        return view
    }

    private fun init() {
        everythingNewsArrayList = ArrayList()
        recyclerViewFromEverythingNews.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter(context, everythingNewsArrayList)
        recyclerViewFromEverythingNews.adapter = newsAdapter
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            101 -> {
                if (user != null) {
                    WorkWithBookmarks().addToBookmarks(item.groupId, newsAdapter)
                    Toast.makeText(requireContext(), "Bookmark added", Toast.LENGTH_SHORT).show()
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

