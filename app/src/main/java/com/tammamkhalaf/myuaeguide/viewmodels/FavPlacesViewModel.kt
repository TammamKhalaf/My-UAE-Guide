package com.tammamkhalaf.myuaeguide.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.tammamkhalaf.myuaeguide.repository.Repository

class FavPlacesViewModel @ViewModelInject constructor(private val repository: Repository):ViewModel(){

}