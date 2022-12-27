package com.example.splashapicompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.splashapicompose.models.UnsplashedRemoteKeys

@Dao
interface UnsplashedRemoteKeyDao {

    @Query("SELECT * FROM unsplash_remote_key_table WHERE id=:id")
    suspend fun getRemoteKeys(id:String):UnsplashedRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys:List<UnsplashedRemoteKeys>)

    @Query("DELETE FROM unsplash_remote_key_table")
    suspend fun deleteAllRemoteKeys()


}