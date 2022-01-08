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
import com.example.testnewsapp.RequestManagerForNewsAPI
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.models.NewsHeadLines


class HomeFragment() : Fragment() {

    private lateinit var newsGeneralArrayList: ArrayList<NewsHeadLines>
    private lateinit var recyclerViewFromGeneral: RecyclerView;
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchView: SearchView
    private var category: String = "general"

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.home_fragment, null)

        recyclerViewFromGeneral = view.findViewById(R.id.recycler_view_of_home)

        searchView = view.findViewById(R.id.search_home)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                init()
                val manager = RequestManagerForNewsAPI()
                manager.findHeadlinesNews(context, category, newsGeneralArrayList, newsAdapter,query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })


        init()

        val manager = RequestManagerForNewsAPI()
        manager.findHeadlinesNews(context, category, newsGeneralArrayList, newsAdapter)
        return view
    }

    private fun init() {
        newsGeneralArrayList = ArrayList()
        recyclerViewFromGeneral.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter(context, newsGeneralArrayList)
        recyclerViewFromGeneral.adapter = newsAdapter
    }

}

