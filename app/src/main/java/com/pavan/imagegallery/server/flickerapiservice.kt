package com.pavan.imagegallery.server

import com.pavan.imagegallery.model.Photos
import com.pavan.imagegallery.model.photoresponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface flickerapiservice {

    @GET("/rest/?method=flickr.photos.getRecent&per_page=20&page=1&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
    suspend fun getallimages(): photoresponse
}