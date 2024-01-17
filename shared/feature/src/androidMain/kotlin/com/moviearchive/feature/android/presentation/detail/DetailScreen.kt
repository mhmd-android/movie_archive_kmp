package com.moviearchive.feature.android.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.moviearchive.feature.model.MovieUiModel
import com.moviearchive.feature.presentation.detail.DetailScreen
import com.moviearchive.feature.presentation.detail.Header
import org.koin.compose.koinInject

@Preview
@Composable
private fun DetailScreenPreview() {
    DetailScreen(
        movieId = 0,
        onBackClicked = {}
    )
}

@Preview
@Composable
private fun HeaderPreview() {
    Header(
        movie = MovieUiModel(
            id = 0,
            imageUrl = "",
            title = "Title",
            numComments = 0,
            numLikes = 0,
            isLiked = false
        ),
        viewModel = koinInject()
    )
}