package com.alessandroborelli.floatapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.alessandroborelli.floatapp.domain.model.Mooring
import com.alessandroborelli.floatapp.ui.base.LoadingContent

@Composable
internal fun MainScreen(
    viewModel: MainViewModel
) {
    val state by viewModel.state.collectAsState()

    Column {
        when {
            state.isLoading -> LoadingContent()
            state.data.isNotEmpty() -> MainContent(state.data)
        }
    }
}

@Composable
fun MainContent(moorings: List<Mooring>) {
    LazyColumn() {
        items(
            items = moorings,
            itemContent = { mooring ->
                MooringItem(item = mooring)
            }
        )
    }
}

@Composable
fun MooringItem(item: Mooring) {
    Text(
        text = item.name
    )
}

@Preview
@Composable
fun PrevMooringItem() {
    val mooring = Mooring(
        arrivedOn = "22 Jan (Tue) at 12:30",
        leftOn = "30 Jan (Tue) at 09:30",
        creationDate = "",
        lastUpdate = "",
        latitude = "51.53582",
        longitude = "-0.06652",
        name = "3"
    )
    MooringItem(item = mooring)
}