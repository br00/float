package com.alessandroborelli.floatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.alessandroborelli.floatapp.presentation.FloatApp
import com.alessandroborelli.floatapp.presentation.MainScreen
import com.alessandroborelli.floatapp.presentation.MainViewModel
import com.alessandroborelli.floatapp.ui.theme.FloatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FloatTheme {
                Surface(color = MaterialTheme.colors.background) {
                    FloatApp()
                }
            }
        }
    }
}

@Composable
private fun MainActivityScreen(viewModel: MainViewModel) {
    MainScreen(viewModel)
}
