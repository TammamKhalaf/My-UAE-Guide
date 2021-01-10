package com.tammamkhalaf.myuaeguide.helperClasses.HomeAdapter.Featured

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tammamkhalaf.myuaeguide.helperClasses.HomeAdapter.Featured.FeaturedAdapter.FeaturedViewHolder
import com.tammamkhalaf.myuaeguide.R
import java.util.*

class FeaturedAdapter(var featuredLocations: ArrayList<FeaturedHelperClass>, var context: Context) : RecyclerView.Adapter<FeaturedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.featured_card_design, parent, false)
        return FeaturedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {
        val featuredHelperClass = featuredLocations[position]
        holder.image.setImageResource(featuredHelperClass.image)
        holder.title.text = featuredHelperClass.title
        holder.description.text = featuredHelperClass.description
        holder.itemView.setOnClickListener { v: View? ->
            val intent = Intent(context, ShowFeaturedPlace::class.java)
            //todo add information about place inside extras and send it to new activity
            intent.putExtra("featuredItemTitle", featuredHelperClass.title)
            intent.putExtra("featuredItemDescription", featuredHelperClass.description)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return featuredLocations.size
    }

    class FeaturedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var title: TextView
        var description: TextView

        init {
            image = itemView.findViewById(R.id.featured_image)
            title = itemView.findViewById(R.id.featured_title)
            description = itemView.findViewById(R.id.featured_desc)
        }
    }
}