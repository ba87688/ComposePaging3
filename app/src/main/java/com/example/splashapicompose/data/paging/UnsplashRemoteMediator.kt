package com.example.splashapicompose.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.splashapicompose.data.local.UnplashedImageDao
import com.example.splashapicompose.data.local.UnsplashDatabase
import com.example.splashapicompose.data.remote.UnsplashAPI
import com.example.splashapicompose.models.UnsplashedImage
import com.example.splashapicompose.models.UnsplashedRemoteKeys
import javax.inject.Inject


    @ExperimentalPagingApi
    class UnsplashRemoteMediator(
        private val unsplashApi: UnsplashAPI,
        private val unsplashDatabase: UnsplashDatabase
    ) : RemoteMediator<Int, UnsplashedImage>() {

        private val unsplashImageDao = unsplashDatabase.unsplashedImageDao()
        private val unsplashRemoteKeysDao = unsplashDatabase.unsplashedRemoteKeyDao()

        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, UnsplashedImage>
        ): MediatorResult {
            return try {
                val currentPage = when (loadType) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                        remoteKeys?.nextPage?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                        val prevPage = remoteKeys?.prevPage
                            ?: return MediatorResult.Success(
                                endOfPaginationReached = remoteKeys != null
                            )
                        prevPage
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                        val nextPage = remoteKeys?.nextPage
                            ?: return MediatorResult.Success(
                                endOfPaginationReached = remoteKeys != null
                            )
                        nextPage
                    }
                }

                val response = unsplashApi.getAllImages(page = currentPage, per_page = 10)
                val endOfPaginationReached = response.isEmpty()

                val prevPage = if (currentPage == 1) null else currentPage - 1
                val nextPage = if (endOfPaginationReached) null else currentPage + 1

                unsplashDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        unsplashImageDao.deleteAllImages()
                        unsplashRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val keys = response.map { unsplashImage ->
                        UnsplashedRemoteKeys(
                            id = unsplashImage.id,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    unsplashRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                    unsplashImageDao.insert(images = response)
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } catch (e: Exception) {
                return MediatorResult.Error(e)
            }
        }

        private suspend fun getRemoteKeyClosestToCurrentPosition(
            state: PagingState<Int, UnsplashedImage>
        ): UnsplashedRemoteKeys? {
            return state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { id ->
                    unsplashRemoteKeysDao.getRemoteKeys(id = id)
                }
            }
        }

        private suspend fun getRemoteKeyForFirstItem(
            state: PagingState<Int, UnsplashedImage>
        ): UnsplashedRemoteKeys? {
            return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
                ?.let { unsplashImage ->
                    unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
                }
        }

        private suspend fun getRemoteKeyForLastItem(
            state: PagingState<Int, UnsplashedImage>
        ): UnsplashedRemoteKeys? {
            return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                ?.let { unsplashImage ->
                    unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
                }
        }

    }