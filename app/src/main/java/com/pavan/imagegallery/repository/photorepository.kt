package com.pavan.imagegallery.repository

import com.pavan.imagegallery.model.Imageitem

interface photorepository {

    suspend fun getimages():List<Imageitem>
}