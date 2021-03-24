package com.tammamkhalaf.kmmsharedmodule.helperClasses.homeAdapter.featured

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured.FeaturedAdapter.FeaturedViewHolder
import com.tammamkhalaf.myuaeguide.R

class FeaturedAdapter(var featuredLocations: ArrayList<FeaturedHelperClass>, var context: Context) :
        RecyclerView.Adapter<FeaturedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.featured_card_design, parent, false)
        return FeaturedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {
        val featuredHelperClass = featuredLocations[position]
        //todo if not work with live data go back to array list featuredLocations[position]

        Glide.with(context).load(featuredLocations[position].imageUrl).into(holder.image)

        holder.title.text = featuredHelperClass.title
        holder.description.text = featuredHelperClass.description
        holder.image.setOnClickListener { val intent = Intent(context, ShowFeaturedPlace::class.java)
            //todo add information about place inside extras and send it to new activity
            intent.putExtra("featuredItemTitle", featuredHelperClass.title)

            intent.putExtra("featuredItemDescription", featuredHelperClass.description)

            intent.putExtra("placeID",featuredHelperClass.id)

            context.startActivity(intent)
        }
        holder.favorite.setOnClickListener{
            //todo add item to favorite activity
        }

    }



    override fun getItemCount(): Int {
        return featuredLocations.size
    }

    class FeaturedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.featured_image)
        var title: TextView = itemView.findViewById(R.id.featured_title)
        var description: TextView = itemView.findViewById(R.id.featured_desc)
        val favorite:ImageView = itemView.findViewById(R.id.favIV)

    }

    companion object {
        private const val TAG = "FeaturedAdapter"
    }
}