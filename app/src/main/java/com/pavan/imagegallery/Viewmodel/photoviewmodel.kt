package com.pavan.imagegallery.Viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.pavan.imagegallery.model.Imageitem
import com.pavan.imagegallery.repository.galleryrepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class photoviewmodel @Inject constructor(val galleryrepository: galleryrepository):ViewModel() {

    private val _imagelist = MutableLiveData<List<Imageitem>>()
    val  imagelist : LiveData<List<Imageitem>> get() = _imagelist

    init {
        loadimages()
    }

    private fun loadimages() {
        viewModelScope.launch {
                try {
                        val images = galleryrepository.getimages()
                        _imagelist.value = images
                }catch (e:Exception){
                    Log.e("Viewmodel","Error loading images",e)
                }
          }
         }
    }
