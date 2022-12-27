package com.example.splashapicompose.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.splashapicompose.util.ConstantsClass.UNSPLASH_REMOTE_KEY_TABLE

@Entity(tableName = UNSPLASH_REMOTE_KEY_TABLE)
data class UnsplashedRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id:String,
    val prevPage:Int?,
    val nextPage:Int?
)
