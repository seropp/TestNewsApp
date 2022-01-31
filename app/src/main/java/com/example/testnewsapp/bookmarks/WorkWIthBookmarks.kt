package com.example.testnewsapp.bookmarks

import android.content.Context
import android.widget.Toast
import com.example.testnewsapp.MainActivity
import com.example.testnewsapp.adapter.NewsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.math.BigInteger
import java.math.MathContext

class WorkWithBookmarks {


    fun deleteBookmark(item: Int, newsAdapter: NewsAdapter, context: Context?) {

        val headline = newsAdapter.newsHLArrayList[item]
        val idForItem = idGenerator(headline.url!!)
        val user = FirebaseAuth.getInstance().currentUser
        newsAdapter.notifyDataSetChanged()

        FirebaseDatabase.getInstance().getReference("users")
            .child(user!!.uid).child("bookmarks")
            .child(idForItem).removeValue().addOnFailureListener {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
    }

    fun addToBookmarks(item: Int, newsAdapter: NewsAdapter) {

        val headline = newsAdapter.newsHLArrayList[item]
        val idForItem = idGenerator(headline.url!!)
        val user = FirebaseAuth.getInstance().currentUser
        newsAdapter.notifyDataSetChanged()

        val path = FirebaseDatabase.getInstance().getReference("users")
            .child(user!!.uid).child("bookmarks")
            .child(idForItem)

        path.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                path.setValue(headline)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun idGenerator(str: String): String {
        val bigInteger = BigInteger(1, str.encodeToByteArray())
        return java.lang.String.format(
            "%0" + (str.encodeToByteArray().size shl 1).toString() + "X",
            bigInteger
        )
    }
}