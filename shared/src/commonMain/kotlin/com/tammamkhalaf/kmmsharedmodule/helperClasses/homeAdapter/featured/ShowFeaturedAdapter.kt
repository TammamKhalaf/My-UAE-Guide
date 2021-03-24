package com.tammamkhalaf.kmmsharedmodule.helperClasses.homeAdapter.featured

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.PlaceResponse

class ShowFeaturedAdapter(var context: Context) : RecyclerView.Adapter<ShowFeaturedAdapter.ShowFeaturedViewHolder>() {
    private var placesList:List<PlaceResponse> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowFeaturedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_place_card_design, parent, false)
        return ShowFeaturedViewHolder(view)
    }

    override fun onBindViewHolder(holderShow: ShowFeaturedViewHolder, position: Int) {

        Glide.with(context).load(placesList[position].icon?:"").into(holderShow.image)//todo this media.images.available?:"" //test why icon must not be null
        Log.d(TAG, "onBindViewHolder: ${placesList[position].icon}")
        holderShow.title.text = placesList[position].name
        holderShow.description.text = placesList[position].contacts?.phone?.toString()?:""

        holderShow.image.setOnClickListener {
            val intent = Intent(context, ShowFeaturedPlace::class.java)
            //todo add information about place inside extras and send it to new activity
            intent.putExtra("featuredItemTitle", placesList[position].name)

            intent.putExtra("featuredItemDescription", placesList[position].view)

            intent.putExtra("placeID",placesList[position].placeId)
            context.startActivity(intent)
        }

        holderShow.favorite.setOnClickListener{
            //todo remove item from favorite activity
        }

    }

    fun setListPlaces(placesList:List<PlaceResponse>){
        this.placesList = placesList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return placesList.size
    }

    class ShowFeaturedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.fav_featured_image)//todo test must not be null
        var title: TextView = itemView.findViewById(R.id.fav_featured_title)
        var description: TextView = itemView.findViewById(R.id.fav_featured_desc)
        val favorite:ImageView = itemView.findViewById(R.id.fav_favIV)
    }

    companion object {
        private const val TAG = "FeaturedAdapter"
    }

}

