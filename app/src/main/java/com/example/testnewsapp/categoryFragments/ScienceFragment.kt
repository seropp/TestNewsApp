package com.example.testnewsapp.categoryFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testnewsapp.R
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.models.NewsHeadLines
import com.example.testnewsapp.RequestManagerForNewsAPI
import kotlin.collections.ArrayList

class ScienceFragment : Fragment() {

    lateinit var newsHeadLinesArrayList: ArrayList<NewsHeadLines>
    lateinit var recyclerViewFromScience: RecyclerView;
    lateinit var newsAdapter: NewsAdapter
    var category: String = "science"

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.science_fragment, null)
        recyclerViewFromScience = view.findViewById(R.id.recycler_view_of_science)
        newsHeadLinesArrayList = ArrayList()
        recyclerViewFromScience.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter(context, newsHeadLinesArrayList)
        recyclerViewFromScience.adapter = newsAdapter

        val manager = RequestManagerForNewsAPI()
        manager.findHeadlinesNews(context, category, newsHeadLinesArrayList, newsAdapter)
        return view
    }
}