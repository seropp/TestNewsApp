package com.example.testnewsapp.bookmarks

import com.example.testnewsapp.adapter.NewsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.math.BigInteger

class WorkWithBookmarks {
    fun addToBookmarks(item: Int, newsAdapter: NewsAdapter, ) {
        val headline = newsAdapter.newsHLArrayList[item]
        val idForItem = idGenerator(headline.url!!)
        val user = FirebaseAuth.getInstance().currentUser
        FirebaseDatabase.getInstance().getReference("users")
            .child(user!!.uid).child("bookmarks")
            .child(idForItem).setValue(headline)
    }

    private fun idGenerator(str: String): String{
        val bigInteger = BigInteger(1, str.encodeToByteArray())
        return java.lang.String.format("%0" + ( str.encodeToByteArray().size shl 1).toString() + "X", bigInteger)
    }
}