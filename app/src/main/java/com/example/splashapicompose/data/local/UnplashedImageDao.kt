package com.example.splashapicompose.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.splashapicompose.models.UnsplashedImage

@Dao
interface UnplashedImageDao {

    // page number and an unsplashed image
    //paginate through our room database by using the return type paginsource
    @Query("SELECT * FROM unsplash_image_table")
    fun getAllImages(): PagingSource<Int, UnsplashedImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(images: List<UnsplashedImage>)


    @Query("DELETE FROM unsplash_image_table")
    suspend fun deleteAllImages()

}