package com.alessandroborelli.floatapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.alessandroborelli.floatapp.ui.base.LoadingContent

@Composable
internal fun MainScreen(
    viewModel: MainViewModel
) {
    val state by viewModel.state.collectAsState()

    Column {
        when {
            state.isLoading -> LoadingContent()
            state.data != null -> MainContent(name = state.data ?: "")
        }
    }
}

@Composable
fun MainContent(name: String) {
    Text(text = name)
}