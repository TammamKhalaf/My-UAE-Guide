package com.tammamkhalaf.myuaeguide.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tammamkhalaf.myuaeguide.R;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class FavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav2);


    }
}