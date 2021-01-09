package com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Categories.CategoriesAdapter.AdapterAllCategoriesViewHolder
import com.tammamkhalaf.myuaeguide.R
import java.util.*

class CategoriesAdapter(var mostViewedLocations: ArrayList<CategoriesHelperClass>) : RecyclerView.Adapter<AdapterAllCategoriesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAllCategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categories_card_design, parent, false)
        return AdapterAllCategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterAllCategoriesViewHolder, position: Int) {
        val helperClass = mostViewedLocations[position]
        holder.imageView.setImageResource(helperClass.image)
        holder.textView.text = helperClass.title
        holder.relativeLayout.background = helperClass.gradient
    }

    override fun getItemCount(): Int {
        return mostViewedLocations.size
    }

    class AdapterAllCategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var relativeLayout: RelativeLayout
        var imageView: ImageView
        var textView: TextView

        init {
            relativeLayout = itemView.findViewById(R.id.background_gradient)
            imageView = itemView.findViewById(R.id.categories_image)
            textView = itemView.findViewById(R.id.categories_title)
        }
    }
}