package com.alessandroborelli.floatapp.presentation.moorings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alessandroborelli.floatapp.domain.model.Mooring
import com.alessandroborelli.floatapp.ui.base.LoadingContent

@Composable
internal fun MooringsScreen(viewModel: MooringsViewModel) {
    val state by viewModel.state.collectAsState()
    Column {
        when {
            state.isLoading -> LoadingContent()
            state.data.isNotEmpty() ->
                MainContent(
                    moorings = state.data,
                    onLeftMooringClicked = {
                        viewModel.onEvent(MooringsUiEvent.LeaveMooring(it))
                    },
                    onAddMooringClicked = {
                        viewModel.onEvent(MooringsUiEvent.AddMooring)
                    }
                )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(
    moorings: List<Mooring>,
    onLeftMooringClicked: (Mooring) -> Unit,
    onAddMooringClicked: () -> Unit,
    modifier: Modifier = Modifier) {

    val backdropState = rememberBackdropScaffoldState(BackdropValue.Revealed)
    val frontLayerHeightDp = LocalConfiguration.current.screenHeightDp / 2

    BackdropScaffold(
        modifier = modifier,
        scaffoldState = backdropState,
        peekHeight = 0.dp,
        headerHeight = frontLayerHeightDp.dp,
        frontLayerScrimColor = Color.Unspecified,
        appBar = {},
        backLayerContent = {
            MooringsMapContent()
        },
        frontLayerContent = {
            MooringsListContent(
                moorings,
                onLeftMooringClicked,
                onAddMooringClicked,
                modifier
            )
        }
    )
}



@Preview
@Composable
fun PrevMoorings() {
    MainContent(emptyList(), {}, {})
}