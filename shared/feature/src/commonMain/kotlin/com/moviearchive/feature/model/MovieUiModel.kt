package com.moviearchive.feature.model

import com.moviearchive.domain.model.MovieDomainModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

data class MovieUiModel(
    val id: String,
    val title: String,
    val image: String,
    val year: Int,
    private val stars: String
) {
    fun getStars(): PersistentList<String> =
        stars
            .split(",")
            .map { it.trim() }
            .toPersistentList()
}

fun MovieDomainModel.toUi() = MovieUiModel(
    id = id,
    title = title,
    image = image,
    year = year,
    stars = stars
)