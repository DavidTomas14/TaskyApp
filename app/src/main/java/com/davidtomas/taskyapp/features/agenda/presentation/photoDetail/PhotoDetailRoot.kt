package com.davidtomas.taskyapp.features.agenda.presentation.photoDetail

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
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

                is PhotoDetailAction.OnDeleteIconClicked -> {
                    navController
                        .previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(
                            AgendaRoutes.IS_PHOTO_DELETED_PARAM,
                            photoDetailViewModel.state.photoUri
                        )
                    navController.popBackStack()
                }
            }
        }
    )
}