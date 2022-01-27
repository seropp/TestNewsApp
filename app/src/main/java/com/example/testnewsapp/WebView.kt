package com.example.testnewsapp

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.testnewsapp.bookmarks.WorkWithBookmarks
import com.example.testnewsapp.models.NewsClass
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class WebView : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    private lateinit var webView: WebView
    private lateinit var shareBT: FloatingActionButton
    private lateinit var bookmarkBT: FloatingActionButton
    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        shareBT = findViewById(R.id.web_view_share_bt)
        bookmarkBT = findViewById(R.id.web_view_bookmarks_bt)
        toolbar = findViewById(R.id.toolbar)
        webView = findViewById(R.id.web_view)

        setSupportActionBar(toolbar)

        val intent: Intent = intent
        val title: String = intent.getStringExtra("title")!!
        val description: String = intent.getStringExtra("description")!!
        val content: String = intent.getStringExtra("content")!!
        val publishedAt: String = intent.getStringExtra("publishedAt")!!
        val author: String = intent.getStringExtra("author")!!
        val source: String = intent.getStringExtra("source")!!
        val imageUrl: String = intent.getStringExtra("imageUrl")!!
        val url: String = intent.getStringExtra("url")!!


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

            val bookmarks: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(user!!.uid).child("bookmarks")
                .child(WorkWithBookmarks().idGenerator(url))
            bookmarks.child("author").setValue(author)
            bookmarks.child("content").setValue(content)
            bookmarks.child("description").setValue(description)
            bookmarks.child("publishedAt").setValue(publishedAt)
            bookmarks.child("source").child("name").setValue(source)
            bookmarks.child("title").setValue(title)
            bookmarks.child("url").setValue(url)
            bookmarks.child("urlToImage").setValue(imageUrl)
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
}