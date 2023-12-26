package com.pavan.imagegallery.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pavan.imagegallery.model.Imageitem
import com.pavan.imagegallery.server.flickerapiservice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class galleryrepository @Inject constructor( private val flickerapiservice: flickerapiservice):photorepository {


    companion object {
        private const val PAGE_SIZE = 20
    }
    override suspend fun getimages(): Flow<PagingData<Imageitem>> {
        return Pager(
                    config = PagingConfig(pageSize = PAGE_SIZE),
                    pagingSourceFactory = { pagingsource(flickerapiservice) }
                ).flow
            }

    suspend fun getsearchimages(query:String): Flow<PagingData<Imageitem>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { searchpagingsource(flickerapiservice,query) }
        ).flow
    }


}

