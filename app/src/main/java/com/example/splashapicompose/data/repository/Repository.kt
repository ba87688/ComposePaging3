package com.example.splashapicompose.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.splashapicompose.data.local.UnsplashDatabase
import com.example.splashapicompose.data.paging.SearchPagingSource
import com.example.splashapicompose.data.paging.UnsplashRemoteMediator
import com.example.splashapicompose.data.remote.UnsplashAPI
import com.example.splashapicompose.models.UnsplashedImage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
@ExperimentalPagingApi
class Repository @Inject constructor(
    private val unsplashApi: UnsplashAPI,
    private val unsplashDatabase: UnsplashDatabase
) {

    fun getAllImages(): Flow<PagingData<UnsplashedImage>> {
        val pagingSourceFactory = { unsplashDatabase.unsplashedImageDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = UnsplashRemoteMediator(
                unsplashApi = unsplashApi,
                unsplashDatabase = unsplashDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun searchImages(query: String): Flow<PagingData<UnsplashedImage>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchPagingSource(unsplashApi = unsplashApi, query = query)
            }
        ).flow
    }

}