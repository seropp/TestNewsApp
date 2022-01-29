package com.example.testnewsapp.navigation_fragments


import android.content.Context
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
import com.example.testnewsapp.models.NewsClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.math.BigInteger


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

        val view: View = inflater.inflate(R.layout.bookmarks_fragment, null)

        headlinesRecyclerView = view.findViewById(R.id.recycler_view_of_bookmarks)
        headlinesRecyclerView.setHasFixedSize(true)

        getBookmarks()



        headlinesRecyclerView = view.findViewById(R.id.recycler_view_of_bookmarks)
        return view
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {


            return when (item.itemId) {
                101 -> {
                    if (user != null) {
                        WorkWithBookmarks().deleteBookmark(
                            item.groupId,
                            newsAdapter,
                            requireContext()
                        )

                        Toast.makeText(requireContext(), "Bookmark deleted", Toast.LENGTH_SHORT)
                            .show()
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



    private fun getBookmarks() {
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
    }
}

