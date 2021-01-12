package com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.mostViewed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.mostViewed.MostViewedAdapter.MostViewedViewHolder
import com.tammamkhalaf.myuaeguide.R
import java.util.*

class MostViewedAdapter(var mostViewedLocations: ArrayList<MostViewedHelperClass>,var context: Context) : RecyclerView.Adapter<MostViewedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostViewedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.most_viewd_card_design, parent, false)
        return MostViewedViewHolder(view)
    }

    override fun onBindViewHolder(holder: MostViewedViewHolder, position: Int) {
        val helperClass = mostViewedLocations[position]

        Glide.with(context).load(mostViewedLocations[position]?.imageView).into(holder.ivImageOfHotel)
        holder.tvNameOfHotel.text = helperClass.textView
        holder.tvDescriptionOfHotel.text = helperClass.description
    }

    override fun getItemCount(): Int {
        return mostViewedLocations.size
    }

    class MostViewedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivImageOfHotel: ImageView = itemView.findViewById(R.id.mv_image)
        var tvNameOfHotel: TextView = itemView.findViewById(R.id.mv_title)
        var tvDescriptionOfHotel: TextView = itemView.findViewById(R.id.mv_desc)

    }
}