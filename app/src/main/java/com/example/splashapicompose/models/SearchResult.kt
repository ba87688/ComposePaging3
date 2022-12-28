package com.example.splashapicompose.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class SearchResult(
    @SerialName("results")
    val image:List<UnsplashedImage>
) {
}