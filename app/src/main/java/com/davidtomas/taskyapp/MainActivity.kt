package com.davidtomas.taskyapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.davidtomas.taskyapp.core.presentation.navigation.TaskyNavHost
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by inject()
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
                    TaskyNavHost(isAuthenticated = viewModel.isAuthenticated.collectAsState().value)
                }
            }
        }
    }

    private fun initSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isAuthChecked.value
            }

            setOnExitAnimationListener { screen ->
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
            }
        }
    }

    companion object {
        const val INTERPOLATION_DURATION = 500L
        const val INTERPOLATION_INITIAL_VALUE = 0.4f
        const val INTERPOLATION_FINAL_VALUE = 0.0f
    }
}
