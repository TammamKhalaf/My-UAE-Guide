package com.tammamkhalaf.myuaeguide.locationOwner

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tammamkhalaf.myuaeguide.R

/**
 * A simple [Fragment] subclass.
 * Use the [RetailerProfileFragment.new Instance] factory method to
 * create an instance of this fragment.
 */
class RetailerProfileFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_retailer_profile, container, false)
    }
}