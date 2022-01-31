package com.example.testnewsapp.user_statistic.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testnewsapp.R
import com.example.testnewsapp.internet_connection.InternetConnection
import com.example.testnewsapp.internet_connection.NetworkManager
import com.example.testnewsapp.web_view.WebView

class StatisticAdapter(var context: Context?, var list: ArrayList<StatisticNewsClass>) :
    RecyclerView.Adapter<StatisticViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticViewHolder {
        return StatisticViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.statistic_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StatisticViewHolder, position: Int) {
        holder.sCardView.setOnClickListener {
            if (NetworkManager.isNetworkAvailable(context)) {

                val intent = Intent(context, WebView::class.java)
                intent.putExtra("title", list[position].title)
                intent.putExtra("description", list[position].description)
                intent.putExtra("content", list[position].content)
                intent.putExtra("publishedAt", list[position].publishedAt)
                intent.putExtra("author", list[position].author)
                intent.putExtra("source", list[position].source!!.name)
                intent.putExtra("imageUrl", list[position].urlToImage)
                intent.putExtra("url", list[position].url)
//                intent.putExtra("time", list[position].time)
                context?.startActivity(intent)

            } else {
                context?.startActivity(Intent(context, InternetConnection::class.java))
            }

        }
        holder.sTitle.text = list[position].title
        holder.sDescription.text = list[position].description
        holder.sAuthor.text = list[position].author
        holder.sSource.text = list[position].source!!.name

        Glide.with(context!!).load(list[position].urlToImage).into(holder.sViewImage)

        val time = "Total time: " + getTime(list[position].time)+" sec"
        holder.sDuration.text = time
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun getTime(l: Long): String{
        return ""+(l/1000)
    }
}