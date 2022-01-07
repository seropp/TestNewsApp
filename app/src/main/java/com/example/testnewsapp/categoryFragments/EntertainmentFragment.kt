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

class EntertainmentFragment : Fragment() {

    lateinit var newsHeadLinesArrayList: ArrayList<NewsHeadLines>
    lateinit var recyclerViewFromEntertainment: RecyclerView;
    lateinit var newsAdapter: NewsAdapter
    var category: String = "entertainment"

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.entertainment_fragment, null)
        recyclerViewFromEntertainment = view.findViewById(R.id.recycler_view_of_entertainment)
        newsHeadLinesArrayList = ArrayList()
        recyclerViewFromEntertainment.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter(context, newsHeadLinesArrayList)
        recyclerViewFromEntertainment.adapter = newsAdapter

        val manager = RequestManagerForNewsAPI()
        manager.findNews(context, category, newsHeadLinesArrayList, newsAdapter)
        return view
    }
}