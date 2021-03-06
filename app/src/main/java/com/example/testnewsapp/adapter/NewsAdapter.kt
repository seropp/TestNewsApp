package com.example.testnewsapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testnewsapp.R
import com.example.testnewsapp.internet_connection.InternetConnection
import com.example.testnewsapp.internet_connection.NetworkManager
import com.example.testnewsapp.login.LoginActivity
import com.example.testnewsapp.web_view.WebView
import com.example.testnewsapp.models.NewsClass

class NewsAdapter(var context: Context?, var newsHLArrayList: ArrayList<NewsClass>) :
    RecyclerView.Adapter<ViewHolder>() {
// ListAdapter
    //applylist() ->
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_layout, parent, false)
        )
    }
    private val position = 0
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.iCardView.setOnClickListener {
            if (NetworkManager.isNetworkAvailable(context)) {

                val intent = Intent(context, WebView::class.java)
                intent.putExtra("title", newsHLArrayList[position].title)
                intent.putExtra("description", newsHLArrayList[position].description)
                intent.putExtra("content", newsHLArrayList[position].content)
                intent.putExtra("publishedAt", newsHLArrayList[position].publishedAt)
                intent.putExtra("author", newsHLArrayList[position].author)
                intent.putExtra("source", newsHLArrayList[position]?.source!!.name)
                intent.putExtra("imageUrl", newsHLArrayList[position].urlToImage)
                intent.putExtra("url", newsHLArrayList[position].url)
                context?.startActivity(intent)

            } else {
                context?.startActivity(Intent(context, InternetConnection::class.java))
            }

        }
        holder.iTitle.text = newsHLArrayList[position].title
        holder.iDescription.text = newsHLArrayList[position].description
        holder.iAuthor.text = newsHLArrayList[position].author
        holder.iSource.text = newsHLArrayList[position].source!!.name

        Glide.with(context!!).load(newsHLArrayList[position].urlToImage).into(holder.iViewImage)

        val time = "Published At:" + newsHLArrayList[position].publishedAt!!.substring(0..9) +
                " " + newsHLArrayList[position].publishedAt!!.substring(11..15)
        holder.iPublicationTime.text = time
    }

    override fun getItemCount(): Int {
        return newsHLArrayList.size
    }

}