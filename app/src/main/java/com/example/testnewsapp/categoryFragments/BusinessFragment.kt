package com.example.testnewsapp.categoryFragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testnewsapp.R
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.models.NewsHeadLines
import com.example.testnewsapp.RequestManagerForNewsAPI
import kotlin.collections.ArrayList


class BusinessFragment : Fragment() {

    private lateinit var recyclerViewFromBusiness: RecyclerView
    private lateinit var newsHeadLinesArrayList: ArrayList<NewsHeadLines>
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchView: SearchView
    private var category: String = "business"

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.business_fragment, null)

        recyclerViewFromBusiness = view.findViewById(R.id.recycler_view_of_business)


        searchView = view.findViewById(R.id.search_business)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                init()
                val manager = RequestManagerForNewsAPI()
                manager.findHeadlinesNews(
                    context,
                    category,
                    newsHeadLinesArrayList,
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
        manager.findHeadlinesNews(context, category, newsHeadLinesArrayList, newsAdapter)


        return view
    }

    private fun init() {
        newsHeadLinesArrayList = ArrayList()
        recyclerViewFromBusiness.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter(context, newsHeadLinesArrayList)
        recyclerViewFromBusiness.adapter = newsAdapter
    }


}