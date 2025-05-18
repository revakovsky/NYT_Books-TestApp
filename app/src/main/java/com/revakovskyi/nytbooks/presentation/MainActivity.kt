package com.revakovskyi.nytbooks.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.revakovskyi.core.presentation.theme.NYTBooksTheme
import com.revakovskyi.nytbooks.navigation.AppNavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                // TODO: add a condition!
                false
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NYTBooksTheme {
                AppNavGraph(isSignedIn = false)
            }
        }
    }

}
