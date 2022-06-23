package com.alessandroborelli.floatapp.presentation.moorings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alessandroborelli.floatapp.data.Constants
import com.alessandroborelli.floatapp.domain.model.Location
import com.alessandroborelli.floatapp.domain.model.Mooring
import com.alessandroborelli.floatapp.presentation.home.FeatureThatRequiresLocationPermission
import com.alessandroborelli.floatapp.ui.base.LoadingContent
import com.alessandroborelli.floatapp.ui.theme.BottomSheetShape
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun MooringsScreen(viewModel: MooringsViewModel, onAddMooringClicked: () -> Unit) {
    val state by viewModel.state.collectAsState()
    FeatureThatRequiresLocationPermission(
        content = {
            Column {
                when {
                    state.isLoading -> LoadingContent()
                    state.data.isNotEmpty() ->
                        MainContent(
                            viewModel = viewModel,
                            moorings = state.data,
                            onLeftMooringClicked = {
                                viewModel.onEvent(MooringsUiEvent.LeaveMooring(it))
                            },
                            onAddMooringClicked = onAddMooringClicked
                            //viewModel.onEvent(MooringsUiEvent.AddMooring)
                        )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MainContent(
    viewModel: MooringsViewModel,
    moorings: List<Mooring>,
    onLeftMooringClicked: (Mooring) -> Unit,
    onAddMooringClicked: () -> Unit,
    modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()
    val backdropState = rememberBackdropScaffoldState(BackdropValue.Revealed)
    val frontLayerHeightDp = LocalConfiguration.current.screenHeightDp / 3
    val currentMooring = moorings.find { it.leftOn.isEmpty() }
    val initialCurrentLocation =
        if (currentMooring?.latitude != null) {
            Location(currentMooring.latitude, currentMooring.longitude)
        } else {
            Constants.LONDON
        }


    BackdropScaffold(
        modifier = modifier,
        scaffoldState = backdropState,
        peekHeight = 0.dp,
        headerHeight = frontLayerHeightDp.dp,
        frontLayerShape = BottomSheetShape,
        frontLayerScrimColor = Color.Transparent,
        appBar = {},
        backLayerContent = {
            MooringsMapContent(viewModel, moorings)
        },
        frontLayerContent = {
            MooringsListContent(
                viewModel,
                moorings,
                onLeftMooringClicked,
                onAddMooringClicked,
                onItemClick = {
                    scope.launch {
                        backdropState.reveal()
                    }
                },
                modifier
            )
        }
    )
}


@Preview
@Composable
fun PrevMoorings() {
    MainContent(hiltViewModel(), emptyList(), {}, {})
}