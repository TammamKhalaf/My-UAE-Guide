package com.tammamkhalaf.myuaeguide.user;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.tammamkhalaf.myuaeguide.R;
import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.PlaceResponse;
import com.tammamkhalaf.myuaeguide.databases.hereDeveloper.AppDatabase;
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured.ShowFeaturedAdapter;
import com.tammamkhalaf.myuaeguide.viewmodels.FavPlacesViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView placesRecyclerView;

    //private FavPlacesViewModel viewModel; todo use viewModel

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fav);

        //viewModel = new ViewModelProvider(this).get(FavPlacesViewModel.class);

        placesRecyclerView = findViewById(R.id.fav_recyclerView);

        final ShowFeaturedAdapter adapter = new ShowFeaturedAdapter(this);

        placesRecyclerView.setAdapter(adapter);


        final AppDatabase appDatabase = AppDatabase.getInstance(this);

        appDatabase.placesDao().getPlaces().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<PlaceResponse>>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onSuccess(@NotNull List<PlaceResponse> placeResponses) {
                adapter.setListPlaces(placeResponses);
            }

            @Override
            public void onError(@NotNull Throwable e) {

            }
        });


    }

}