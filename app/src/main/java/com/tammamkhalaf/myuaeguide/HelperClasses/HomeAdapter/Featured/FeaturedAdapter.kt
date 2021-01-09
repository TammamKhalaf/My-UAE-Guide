package com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Featured;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tammamkhalaf.myuaeguide.R;
import com.tammamkhalaf.myuaeguide.User.UserDashboard;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    ArrayList<FeaturedHelperClass> featuredLocations;
    Context context;

    public FeaturedAdapter(ArrayList<FeaturedHelperClass> featuredLocations, Context context) {
        this.featuredLocations = featuredLocations;
        this.context = context;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design,parent,false);

        FeaturedViewHolder featuredViewHolder = new FeaturedViewHolder(view);

        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NotNull FeaturedAdapter.FeaturedViewHolder holder, int position) {
        FeaturedHelperClass featuredHelperClass = featuredLocations.get(position);
        holder.image.setImageResource(featuredHelperClass.getImage());
        holder.title.setText(featuredHelperClass.getTitle());
        holder.description.setText(featuredHelperClass.getDescription());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context,ShowFeaturedPlace.class);
            //todo add information about place inside extras and send it to new activity
            intent.putExtra("featuredItemTitle",featuredHelperClass.getTitle());
            intent.putExtra("featuredItemDescription",featuredHelperClass.getDescription());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();
    }


    public static class FeaturedViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title,description;

        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.featured_image);
            title = itemView.findViewById(R.id.featured_title);
            description = itemView.findViewById(R.id.featured_desc);
        }
    }

}
