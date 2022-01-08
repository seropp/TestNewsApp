package com.example.testnewsapp.categoryFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testnewsapp.R
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.models.NewsHeadLines
import com.example.testnewsapp.RequestManagerForNewsAPI
import kotlin.collections.ArrayList

class ScienceFragment() : Fragment() {

    private lateinit var newsHeadLinesArrayList: ArrayList<NewsHeadLines>
    private lateinit var recyclerViewFromScience: RecyclerView;
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchView: SearchView

    var category: String = "science"

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.science_fragment, null)

        recyclerViewFromScience = view.findViewById(R.id.recycler_view_of_science)


        searchView = view.findViewById(R.id.search_science)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                init()
                val manager = RequestManagerForNewsAPI()
                manager.findHeadlinesNews(context, category, newsHeadLinesArrayList, newsAdapter,query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        init()


        val manager = RequestManagerForNewsAPI()
        manager.findHeadlinesNews(context, category, newsHeadLinesArrayList, newsAdapter)
        return view
    }

    private fun init() {
        newsHeadLinesArrayList = ArrayList()
        recyclerViewFromScience.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter(context, newsHeadLinesArrayList)
        recyclerViewFromScience.adapter = newsAdapter
    }

}