package com.example.testnewsapp.user_statistic

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testnewsapp.GetCurrentData
import com.example.testnewsapp.R
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.internet_connection.InternetConnection
import com.example.testnewsapp.internet_connection.NetworkManager
import com.example.testnewsapp.models.NewsClass
import com.example.testnewsapp.user_statistic.adapter.StatisticAdapter
import com.example.testnewsapp.user_statistic.adapter.StatisticNewsClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class StatisticFragment : Fragment() {

    private lateinit var headlinesRecyclerView: RecyclerView
    private var list: ArrayList<StatisticNewsClass> = arrayListOf()
    private lateinit var adapter: StatisticAdapter

    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var allStatistic: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("users")
            .child(user!!.uid).child("statistic")

    private lateinit var totalTime: TextView

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistic, container, false)

        headlinesRecyclerView = view.findViewById(R.id.statistic_recycler_view)
        headlinesRecyclerView.setHasFixedSize(true)
        registerForContextMenu(headlinesRecyclerView)

        totalTime = view.findViewById(R.id.statistic_total_time)

        val path: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("users")
                .child(user!!.uid)

        path.child("info").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                totalTime.text = GetCurrentData().getTotalTime(requireContext()).toString()

            }

            override fun onCancelled(error: DatabaseError) {}
        })

        getStatistic()

        return view
    }


    private fun getStatistic() {
        if (NetworkManager.isNetworkAvailable(requireContext())) {
            allStatistic.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        list.removeAll { true }
                        for (dataSnapshot in snapshot.children) {

                            val news = dataSnapshot.getValue(StatisticNewsClass::class.java)
                            list.add(news!!)
                        }
                        if (context != null) {
                            headlinesRecyclerView.layoutManager = LinearLayoutManager(context)
                            adapter = StatisticAdapter(requireContext(), list)
                            headlinesRecyclerView.adapter = adapter
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {}
            })
        } else {
            requireContext().startActivity(Intent(requireContext(), InternetConnection::class.java))
        }
    }
}