package com.moviearchive.data.source.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieApiModel(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("image") val imageUrl: String,
    @SerialName("year") val year: Int,
    @SerialName("stars") val stars: String
)
