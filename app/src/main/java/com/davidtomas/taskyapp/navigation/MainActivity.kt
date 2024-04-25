package com.davidtomas.taskyapp.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.davidtomas.taskyapp.core.presentation.TaskyUiEvent
import com.davidtomas.taskyapp.core.presentation.TaskyUiEventsChannel
import com.davidtomas.taskyapp.core.presentation.util.ObserveAsEvents
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by inject()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSplashScreen()
        setContent {
            TaskyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val snackbarHostState = remember { SnackbarHostState() }
                    ObserveTaskyUiEvents(snackbarHostState)
                    Scaffold(
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                        content = {
                            TaskyNavHost(
                                isAuthenticated = viewModel.isAuthenticated.collectAsState().value,
                                isAuthChecked = viewModel.isAuthChecked.collectAsState().value
                            )
                        }
                    )
                    WindowCompat.getInsetsController(window, window.decorView).apply {
                        isAppearanceLightStatusBars =
                            false // Set to false if you want dark status bar icons
                    }
                }
            }
        }
    }

    @Composable
    private fun ObserveTaskyUiEvents(snackbarHostState: SnackbarHostState) {
        ObserveAsEvents(flow = TaskyUiEventsChannel.taskyUiEvent) { taskyEvent ->
            when (taskyEvent) {
                is TaskyUiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = taskyEvent.message.asString(this@MainActivity),
                        actionLabel = "Dismiss",
                        duration = SnackbarDuration.Long,
                    )
                }
            }
        }
    }

    private fun initSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isSplashFinished.value
            }

            /*setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    INTERPOLATION_INITIAL_VALUE,
                    INTERPOLATION_FINAL_VALUE
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = INTERPOLATION_DURATION
                zoomX.doOnEnd { screen.remove() }
                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    INTERPOLATION_INITIAL_VALUE,
                    INTERPOLATION_FINAL_VALUE
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = INTERPOLATION_DURATION
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }*/
        }
    }

    companion object {
        const val INTERPOLATION_DURATION = 500L
        const val INTERPOLATION_INITIAL_VALUE = 0.4f
        const val INTERPOLATION_FINAL_VALUE = 0.0f
    }
}
