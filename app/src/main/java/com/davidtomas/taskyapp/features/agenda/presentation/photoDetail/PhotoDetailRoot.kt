package com.davidtomas.taskyapp.features.agenda.presentation.photoDetail

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun PhotoDetailRoot(
    navController: NavHostController,
    photoDetailViewModel: PhotoDetailViewModel = koinViewModel(),
) {
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