package com.pavan.imagegallery.repository

import androidx.paging.PagingData
import com.pavan.imagegallery.model.Imageitem
import kotlinx.coroutines.flow.Flow

interface photorepository {

    suspend fun getimages(): Flow<PagingData<Imageitem>>
}