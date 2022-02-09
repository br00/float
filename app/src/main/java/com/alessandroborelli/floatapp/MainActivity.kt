package com.alessandroborelli.floatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.alessandroborelli.floatapp.data.model.State
import com.alessandroborelli.floatapp.presentation.MainScreen
import com.alessandroborelli.floatapp.presentation.MainViewModel
import com.alessandroborelli.floatapp.ui.theme.FloatTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FloatTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainActivityScreen(viewModel)
                }
            }
        }
    }
}

@Composable
private fun MainActivityScreen(viewModel: MainViewModel) {
    MainScreen(viewModel.ownerState.value)
}
