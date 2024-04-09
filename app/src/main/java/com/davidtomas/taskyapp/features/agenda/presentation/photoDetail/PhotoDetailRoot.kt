package com.davidtomas.taskyapp.features.agenda.presentation.photoDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun PhotoDetailRoot(
    navController: NavHostController,
    photoDetailViewModel: PhotoDetailViewModel = koinViewModel(),
) {

    LaunchedEffect(key1 = true) {
        photoDetailViewModel.uiEvent.collect { event ->
            when (event) {
                is PhotoDetailUiEvent.NavigateUp -> {
                    navController.navigateUp()
                }
            }
        }
    }

    PhotoDetailScreen(
        state = photoDetailViewModel.state,
        onAction = { photoDetailAction ->
            when (photoDetailAction) {

                is PhotoDetailAction.OnCloseIconClicked -> {
                    navController.navigateUp()
                }
                else -> photoDetailViewModel.onAction(photoDetailAction)
            }
        }
    )
}