package com.example.testnewsapp.navigation_fragments


import android.content.Intent
import com.google.firebase.database.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testnewsapp.R
import com.example.testnewsapp.adapter.NewsAdapter
import com.example.testnewsapp.bookmarks.WorkWithBookmarks
import com.example.testnewsapp.internet_connection.InternetConnection
import com.example.testnewsapp.internet_connection.NetworkManager
import com.example.testnewsapp.models.NewsClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class BookmarksFragment : Fragment() {

    private lateinit var headlinesRecyclerView: RecyclerView
    private var listOfBookmarks: ArrayList<NewsClass> = arrayListOf()
    private lateinit var newsAdapter: NewsAdapter

    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var allBookmarks: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("users")
            .child(user!!.uid).child("bookmarks")

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.bookmarks_fragment, container, false)


        headlinesRecyclerView = view.findViewById(R.id.recycler_view_of_bookmarks)
        headlinesRecyclerView.setHasFixedSize(true)
        registerForContextMenu(headlinesRecyclerView)
        getBookmarks()



        return view
    }


    private fun getBookmarks() {
        if (NetworkManager.isNetworkAvailable(requireContext())) {
            allBookmarks.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        listOfBookmarks.removeAll { true }
                        for (dataSnapshot in snapshot.children) {

                            val news = dataSnapshot.getValue(NewsClass::class.java)
                            listOfBookmarks.add(news!!)
                        }
                        if (context != null) {
                            headlinesRecyclerView.layoutManager = LinearLayoutManager(context)
                            newsAdapter = NewsAdapter(requireContext(), listOfBookmarks)
                            headlinesRecyclerView.adapter = newsAdapter
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {}
            })
        } else {
            requireContext().startActivity(Intent(requireContext(), InternetConnection::class.java))
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            131 -> {
                if (user != null) {
                    if (NetworkManager.isNetworkAvailable(requireContext())) {

                        WorkWithBookmarks().deleteBookmark(
                            item.groupId,
                            newsAdapter,
                            requireContext()
                        )
                    } else {
                        requireContext().startActivity(
                            Intent(
                                requireContext(),
                                InternetConnection::class.java
                            )
                        )
                    }

                    Toast.makeText(requireContext(), "Bookmark deleted", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Bookmarks are available only to authorized users",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return true
            }
            else -> super.onContextItemSelected(item)
        }

    }

}

