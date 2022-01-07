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
import com.example.testnewsapp.RequestManagerForNewsAPI
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.models.NewsHeadLines


class HomeFragment : Fragment() {

    lateinit var newsEverythingArrayList: ArrayList<NewsHeadLines>
    lateinit var recyclerViewFromHome: RecyclerView;
    lateinit var newsAdapter: NewsAdapter

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.home_fragment, null)
        recyclerViewFromHome = view.findViewById(R.id.recycler_view_of_home)
        newsEverythingArrayList = ArrayList()
        recyclerViewFromHome.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter(context, newsEverythingArrayList)
        recyclerViewFromHome.adapter = newsAdapter

        val manager = RequestManagerForNewsAPI()
        manager.findNewsForEverything(context, newsEverythingArrayList, newsAdapter)
        return view
    }
}

