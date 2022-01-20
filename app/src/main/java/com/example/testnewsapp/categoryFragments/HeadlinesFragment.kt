package com.example.testnewsapp.categoryFragments

import android.R.attr
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testnewsapp.R
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.models.NewsClass
import com.example.testnewsapp.RequestManagerForNewsAPI
import com.example.testnewsapp.bookmarks.WorkWithBookmarks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.collections.ArrayList
import android.R.attr.defaultValue


class HeadlinesFragment(var category: String) : Fragment() {

    private lateinit var headlinesRecyclerView: RecyclerView
    private lateinit var list: ArrayList<NewsClass>
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchView: SearchView

    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.headlines_fragment, null)


        Log.i(TAG, "Это мое сообщение для записи в журнале  $category");

        headlinesRecyclerView = view.findViewById(R.id.recycler_view_of_headlines)
        searchView = view.findViewById(R.id.search_for_headlines)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                init()
                val manager = RequestManagerForNewsAPI()
                manager.findHeadlinesNews(
                    context,
                    category,
                    list,
                    newsAdapter,
                    query
                )
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        init()

        val manager = RequestManagerForNewsAPI()
        manager.findHeadlinesNews(context, category, list, newsAdapter)
        return view

    }

    private fun init() {
        list = ArrayList()
        headlinesRecyclerView.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter(context, list)
        headlinesRecyclerView.adapter = newsAdapter
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