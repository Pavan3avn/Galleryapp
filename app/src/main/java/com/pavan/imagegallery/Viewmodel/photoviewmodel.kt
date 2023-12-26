package com.pavan.imagegallery.Viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn

import com.pavan.imagegallery.model.Imageitem
import com.pavan.imagegallery.repository.galleryrepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class photoviewmodel @Inject constructor(val galleryrepository: galleryrepository):ViewModel() {

    private val _imagelist = MutableLiveData<PagingData<Imageitem>>()
    val  imagelist : LiveData<PagingData<Imageitem>> get() = _imagelist

    private val _snackbarMessage = MutableLiveData<String>()
    val snackbarMessage: LiveData<String> get() = _snackbarMessage

    init {
        loadrecentimages()
    }

    fun loadrecentimages() {
        viewModelScope.launch {
            try {
                galleryrepository.getimages().cachedIn(viewModelScope).collectLatest { images ->
                    _imagelist.value = images
                }
            } catch (e: Exception) {
                handleNetworkError(e)
                Log.e("Viewmodel", "Error loading recent images", e)
            }
        }
    }

    fun searchImages(query: String) {
        loadimages(query)
    }

    private fun loadimages(query: String?) {
        viewModelScope.launch {
                try {
                    if (query != null) {
                        galleryrepository.getsearchimages(query).cachedIn(viewModelScope).collectLatest {images ->
                            _imagelist.value = images
                        }
                    }
                }catch (e:Exception){
                    handleNetworkError(e)
                    Log.e("Viewmodel","Error loading images",e)
                }
          }
         }

    private fun handleNetworkError(exception: Exception) {
        val errorMessage = if (exception is IOException) {
            "Network error. Please check your internet connection."
        } else {
            "Error loading images. Please try again."
        }
        _snackbarMessage.value = errorMessage
    }

    fun onSnackbarShown() {
        _snackbarMessage.value = null
    }
}
