package com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.mostViewed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.mostViewed.MostViewedAdapter.MostViewedViewHolder
import java.util.*


class MostViewedAdapter(var mostViewedLocations: ArrayList<MostViewedHelperClass>, var context: Context) :
        RecyclerView.Adapter<MostViewedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostViewedViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.most_viewd_card_design, parent, false)
        return MostViewedViewHolder(view)
    }

    override fun onBindViewHolder(holder: MostViewedViewHolder, position: Int) {
        val helperClass = mostViewedLocations[position]

        Glide.with(context)
                .load(mostViewedLocations[position].image)
                .override(150, 150) // resizes the image to these dimensions (in pixel)
                .centerCrop() // this cropping technique scales the image
                .into(holder.imageViewOfPlace)

        holder.tvTitleEnglish.text = helperClass.titleEnglish
        holder.tvTitleArabic.text = helperClass.titleArabic
        holder.tvCategory.text = helperClass.Category
        holder.tvOpeningHoursLabel.text = helperClass.OpeningHourLabel
        holder.tvOpeningHoursText.text = helperClass.OpeningHourText
        holder.ratingBar.rating = helperClass.rating.toFloat()
    }

    override fun getItemCount(): Int {
        return mostViewedLocations.size
    }

    class MostViewedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewOfPlace: ImageView = itemView.findViewById(R.id.mv_image)
        var tvTitleEnglish: TextView = itemView.findViewById(R.id.mv_title_en)
        var tvTitleArabic: TextView = itemView.findViewById(R.id.mv_title_ar)
        var tvCategory: TextView = itemView.findViewById(R.id.mv_category)
        var tvOpeningHoursLabel: TextView = itemView.findViewById(R.id.mv_opening_hours_label)
        var tvOpeningHoursText: TextView = itemView.findViewById(R.id.mv_opening_hours_text)
        var ratingBar: RatingBar = itemView.findViewById(R.id.mv_rating) as RatingBar
    }
}