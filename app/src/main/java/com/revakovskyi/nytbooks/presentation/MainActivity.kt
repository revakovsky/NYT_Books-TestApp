package com.revakovskyi.nytbooks.presentation

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.revakovskyi.core.presentation.design_system.DefaultSnackBarHost
import com.revakovskyi.core.presentation.theme.NYTBooksTheme
import com.revakovskyi.core.presentation.utils.ObserveSingleEvent
import com.revakovskyi.core.presentation.utils.snack_bar.SnackBarAction
import com.revakovskyi.core.presentation.utils.snack_bar.SnackBarController
import com.revakovskyi.core.presentation.utils.snack_bar.SnackBarEvent
import com.revakovskyi.nytbooks.R
import com.revakovskyi.nytbooks.navigation.AppNavGraph
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition { viewModel.state.value.isSignedIn == null }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NYTBooksTheme {
                MainContent(viewModel)
            }
        }
    }

}


@Composable
private fun MainContent(viewModel: MainViewModel) {

    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }


    ObserveSingleEvent(flow = viewModel.event) { event ->
        when (event) {
            MainEvent.ShowInternetNotification -> {
                SnackBarController.sendEvent(
                    SnackBarEvent(
                        message = context.getString(R.string.no_internet_connection),
                        snackBarDuration = SnackbarDuration.Long,
                        action = SnackBarAction(
                            name = context.getString(R.string.settings),
                            action = { settingsLauncher.launch(Intent(Settings.ACTION_WIFI_SETTINGS)) }
                        )
                    )
                )
            }
        }
    }


    Scaffold(
        snackbarHost = { DefaultSnackBarHost(snackBarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val state = viewModel.state.collectAsStateWithLifecycle().value

            Crossfade(targetState = state.isSignedIn, label = "") { isSignedIn ->
                isSignedIn?.let {
                    AppNavGraph(isSignedIn = it)
                }
            }
        }

    }

}
