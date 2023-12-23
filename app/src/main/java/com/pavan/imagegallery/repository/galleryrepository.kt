package com.pavan.imagegallery.repository

import android.util.Log
import com.pavan.imagegallery.model.Imageitem
import com.pavan.imagegallery.server.flickerapiservice
import javax.inject.Inject

class galleryrepository @Inject constructor( private val flickerapiservice: flickerapiservice):photorepository {

    override suspend fun getimages():List<Imageitem>{
        return try {
            val response =flickerapiservice.getallimages()

            if(response.stat == "ok"){
                response.photos?.photo?.map { Photo ->
                     val imageurl = "https://farm${Photo.farm}.staticflickr.com/${Photo.server}/${Photo.id}_${Photo.secret}.jpg"
                    Imageitem(imageurl)
                } ?: emptyList()
            } else{
                emptyList()
            }
        } catch (e: Exception){
            emptyList()
        }
    }
}