package com.example.splashapicompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.splashapicompose.models.UnsplashedImage
import com.example.splashapicompose.models.UnsplashedRemoteKeys

@Database(entities = [UnsplashedImage::class,UnsplashedRemoteKeys::class], version = 1)
abstract class UnsplashDatabase:RoomDatabase() {

    abstract fun unsplashedImageDao():UnplashedImageDao
    abstract fun unsplashedRemoteKeyDao():UnsplashedRemoteKeyDao


}