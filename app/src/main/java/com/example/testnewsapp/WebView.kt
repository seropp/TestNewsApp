package com.example.testnewsapp

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WebView : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    private lateinit var webView: WebView
    private lateinit var shareBT: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        shareBT = findViewById(R.id.web_view_share_bt)
        toolbar = findViewById(R.id.toolbar)
        webView = findViewById(R.id.web_view)

        setSupportActionBar(toolbar)

        val intent: Intent = intent
        val url: String = intent.getStringExtra("url").toString()

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
    }
}