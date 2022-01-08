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

class SportsFragment : Fragment() {

    private lateinit var newsHeadLinesArrayList: ArrayList<NewsHeadLines>
    private lateinit var recyclerViewFromSports: RecyclerView;
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchView: SearchView

    var category: String = "sports"

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.sports_fragment, null)

        recyclerViewFromSports = view.findViewById(R.id.recycler_view_of_sports)


        searchView = view.findViewById(R.id.search_sports)
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
        recyclerViewFromSports.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter(context, newsHeadLinesArrayList)
        recyclerViewFromSports.adapter = newsAdapter
    }
}