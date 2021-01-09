package com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewed.MostViewedAdapter.MostViewedViewHolder
import com.tammamkhalaf.myuaeguide.R
import java.util.*

class MostViewedAdapter(var mostViewedLocations: ArrayList<MostViewedHelperClass>) : RecyclerView.Adapter<MostViewedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostViewedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.most_viewd_card_design, parent, false)
        return MostViewedViewHolder(view)
    }

    override fun onBindViewHolder(holder: MostViewedViewHolder, position: Int) {
        val helperClass = mostViewedLocations[position]
        holder.ivImageOfHotel.setImageResource(helperClass.imageView)
        holder.tvNameOfHotel.text = helperClass.textView
        holder.tvDescriptionOfHotel.text = helperClass.description
    }

    override fun getItemCount(): Int {
        return mostViewedLocations.size
    }

    class MostViewedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivImageOfHotel: ImageView
        var tvNameOfHotel: TextView
        var tvDescriptionOfHotel: TextView

        init {
            ivImageOfHotel = itemView.findViewById(R.id.mv_image)
            tvNameOfHotel = itemView.findViewById(R.id.mv_title)
            tvDescriptionOfHotel = itemView.findViewById(R.id.mv_desc)
        }
    }
}