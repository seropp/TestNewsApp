package com.example.testnewsapp.web_view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.testnewsapp.GetCurrentData
import com.example.testnewsapp.R
import com.example.testnewsapp.bookmarks.WorkWithBookmarks
import com.example.testnewsapp.internet_connection.InternetConnection
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class WebView : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var webView: WebView
    private lateinit var shareBT: FloatingActionButton
    private lateinit var bookmarkBT: FloatingActionButton
    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    private var title: String? = null
    private var description: String? = null
    private var content: String? = null
    private var publishedAt: String? = null
    private var author: String? = null
    private var source: String? = null
    private var imageUrl: String? = null
    private lateinit var url: String

    private var startTime: Long = 0
    private var endTime: Long = 0
    private var totalTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        startTime = System.currentTimeMillis()


        shareBT = findViewById(R.id.web_view_share_bt)
        bookmarkBT = findViewById(R.id.web_view_bookmarks_bt)
        toolbar = findViewById(R.id.toolbar)
        webView = findViewById(R.id.web_view)

        setSupportActionBar(toolbar)

        val intent: Intent = intent
        title = intent.getStringExtra("title")
        description = intent.getStringExtra("description")
        content = intent.getStringExtra("content")
        publishedAt = intent.getStringExtra("publishedAt")
        author = intent.getStringExtra("author")
        source = intent.getStringExtra("source")
        imageUrl = intent.getStringExtra("imageUrl")
        url = intent.getStringExtra("url")!!



        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)

        shareBT.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = webView.url
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)

            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Catch:")
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

        bookmarkBT.setOnClickListener {
            if (user != null) {

                val user = FirebaseAuth.getInstance().currentUser

                val bookmarks: DatabaseReference =
                    FirebaseDatabase.getInstance().getReference("users")
                        .child(user!!.uid).child("bookmarks")


                bookmarks.child(WorkWithBookmarks().idGenerator(url))
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            bookmarks.child(WorkWithBookmarks().idGenerator(url)).child("author")
                                .setValue(author)
                            bookmarks.child(WorkWithBookmarks().idGenerator(url)).child("content")
                                .setValue(content)
                            bookmarks.child(WorkWithBookmarks().idGenerator(url))
                                .child("description").setValue(description)
                            bookmarks.child(WorkWithBookmarks().idGenerator(url))
                                .child("publishedAt").setValue(publishedAt)
                            bookmarks.child(WorkWithBookmarks().idGenerator(url)).child("source")
                                .child("name").setValue(source)
                            bookmarks.child(WorkWithBookmarks().idGenerator(url)).child("title")
                                .setValue(title)
                            bookmarks.child(WorkWithBookmarks().idGenerator(url)).child("url")
                                .setValue(url)
                            bookmarks.child(WorkWithBookmarks().idGenerator(url))
                                .child("urlToImage").setValue(imageUrl)
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
                Toast.makeText(this, "Bookmark added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Bookmarks are available only to authorized users",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroy() {

        endTime = System.currentTimeMillis()

        setStatistic()
        setStatisticTime()
        super.onDestroy()
    }

    private fun setStatistic() {
        if (user != null) {

            val user = FirebaseAuth.getInstance().currentUser

            val statistic: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(user!!.uid).child("statistic")

            statistic.child(WorkWithBookmarks().idGenerator(url))
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        statistic.child(WorkWithBookmarks().idGenerator(url)).child("content")
                            .setValue(content)
                        statistic.child(WorkWithBookmarks().idGenerator(url)).child("description")
                            .setValue(description)
                        statistic.child(WorkWithBookmarks().idGenerator(url)).child("author")
                            .setValue(author)
                        statistic.child(WorkWithBookmarks().idGenerator(url)).child("publishedAt")
                            .setValue(publishedAt)
                        statistic.child(WorkWithBookmarks().idGenerator(url)).child("source")
                            .child("name").setValue(source)
                        statistic.child(WorkWithBookmarks().idGenerator(url)).child("title")
                            .setValue(title)
                        statistic.child(WorkWithBookmarks().idGenerator(url)).child("url")
                            .setValue(url)
                        statistic.child(WorkWithBookmarks().idGenerator(url)).child("urlToImage")
                            .setValue(imageUrl)
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }
    }

    private fun setStatisticTime() {

        val user = FirebaseAuth.getInstance().currentUser
        val commonTime: Long = endTime - startTime


        val path: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("users")
                .child(user!!.uid)

        path.child("info").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                totalTime = snapshot.child("total_time").getValue(Long::class.java)!!
                val pref: SharedPreferences =
                getSharedPreferences("user_settings", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.apply {
                    putLong("TIME", totalTime)
                }.apply()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        totalTime = GetCurrentData().getTotalTime(this)+commonTime
        val pref: SharedPreferences =
            getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.apply {
            putLong("TIME", totalTime)
        }.apply()





        path.child("info")
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        path.child("info").child("total_time")
                            .setValue(GetCurrentData().getTotalTime(this@WebView))

                    }

                    override fun onCancelled(error: DatabaseError) {}
                })


        path.child("statistic").child(WorkWithBookmarks().idGenerator(url))
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        path.child("statistic").child(WorkWithBookmarks().idGenerator(url))
                            .child("time")
                            .setValue(commonTime)
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
    }

}