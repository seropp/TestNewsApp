package com.example.testnewsapp.user_statistic.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.testnewsapp.R


class StatisticViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


    var sTitle: TextView = itemView.findViewById(R.id.statistic_item_title)
    var sDescription: TextView = itemView.findViewById(R.id.statistic_item_description)
    var sAuthor: TextView = itemView.findViewById(R.id.statistic_item_author)
    var sDuration: TextView = itemView.findViewById(R.id.statistic_item_duration_time)
    var sSource: TextView = itemView.findViewById(R.id.statistic_item_source)
    var sViewImage: ImageView = itemView.findViewById(R.id.statistic_item_image)
    var sCardView: CardView = itemView.findViewById(R.id.statistic_cardView)


}


