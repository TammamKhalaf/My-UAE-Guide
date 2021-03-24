package com.tammamkhalaf.myuaeguide.user

import androidx.appcompat.app.AppCompatActivity

class FavoriteActivity : AppCompatActivity() {
    private var placesRecyclerView: RecyclerView? = null

    //private FavPlacesViewModel viewModel; todo use viewModel
    protected fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_fav)

        //viewModel = new ViewModelProvider(this).get(FavPlacesViewModel.class);
        placesRecyclerView = findViewById(R.id.fav_recyclerView)
        val adapter = ShowFeaturedAdapter(this)
        placesRecyclerView.setAdapter(adapter)
        val appDatabase: AppDatabase = AppDatabase.getInstance(this)
        appDatabase.placesDao().getPlaces().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : SingleObserver<List<PlaceResponse?>?>() {
                    fun onSubscribe(@NotNull d: Disposable?) {}
                    fun onSuccess(@NotNull placeResponses: List<PlaceResponse?>?) {
                        adapter.setListPlaces(placeResponses)
                    }

                    fun onError(@NotNull e: Throwable?) {}
                })
    }
}