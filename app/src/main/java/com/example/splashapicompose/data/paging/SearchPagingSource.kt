package com.example.splashapicompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.splashapicompose.data.remote.UnsplashAPI
import com.example.splashapicompose.models.UnsplashedImage

class SearchPagingSource(
    private val unsplashApi: UnsplashAPI,
    private val query: String
) : PagingSource<Int, UnsplashedImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashedImage> {
        val currentPage = params.key ?: 1
        return try {
            val response = unsplashApi.searchImages(page = query,  per_page= 10)
            val endOfPaginationReached = response.image.isEmpty()
            if (response.image.isNotEmpty()) {
                LoadResult.Page(
                    data = response.image,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashedImage>): Int? {
        return state.anchorPosition
    }

}