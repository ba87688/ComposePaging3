package com.example.splashapicompose.data.remote

import com.example.splashapicompose.BuildConfig
import com.example.splashapicompose.models.UnsplashedImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface UnsplashAPI {

    @Headers("Authorization: Client-ID ${BuildConfig.YOUR_ACCESS_KEY}")
    @GET("/photos")
    suspend fun getAllImages(
        @Query("page") page:Int,
        @Query("per_page") per_page:Int
    ): List<UnsplashedImage>

    @Headers("Authorization: Client-ID ${BuildConfig.YOUR_ACCESS_KEY}")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("page") page:Int,
        @Query("per_page") per_page:Int
    ): List<UnsplashedImage>

}