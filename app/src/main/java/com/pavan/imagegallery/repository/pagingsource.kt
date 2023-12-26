package com.pavan.imagegallery.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pavan.imagegallery.model.Imageitem
import com.pavan.imagegallery.server.flickerapiservice

class pagingsource(private val flickerapiservice: flickerapiservice,
                   ):
    PagingSource<Int, Imageitem>() {

    private var recentImagesPage = 1

    override fun getRefreshKey(state: PagingState<Int, Imageitem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Imageitem> {
        return try {
            val page = params.key ?: 1

           if(recentImagesPage > 3){
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
            val response = flickerapiservice.getallimages(page)


            if(response.isSuccessful){
                val images = response.body()?.photos?.photo?.map { Photo ->
                    val imageurl = "https://farm${Photo.farm}.staticflickr.com/${Photo.server}/${Photo.id}_${Photo.secret}.jpg"
                    Imageitem(imageurl,Photo.title)
                }
                if (!images.isNullOrEmpty()) {
                    recentImagesPage++
                }
                LoadResult.Page(
                    data = images ?: emptyList(),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (images?.isEmpty() == true) null else page + 1)
            } else{
                LoadResult.Error(Exception("Failed to load images"))
            }
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}