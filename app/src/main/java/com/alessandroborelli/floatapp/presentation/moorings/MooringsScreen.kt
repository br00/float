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
import com.alessandroborelli.floatapp.ui.base.BlanketScaffold
import com.alessandroborelli.floatapp.ui.base.BlanketValue
import com.alessandroborelli.floatapp.ui.base.LoadingContent
import com.alessandroborelli.floatapp.ui.base.rememberBlanketScaffoldState
import com.google.accompanist.permissions.ExperimentalPermissionsApi

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
                            onAddMooringClicked = onAddMooringClicked,
                            onEditMooringClicked = {

                            },
                            onDeleteMooringClicked = {
                                viewModel.onEvent(MooringsUiEvent.DeleteMooring(it))
                            }
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
    onEditMooringClicked: (Mooring) -> Unit,
    onDeleteMooringClicked: (Mooring) -> Unit,
    modifier: Modifier = Modifier) {

    val frontLayerHeightDp = (LocalConfiguration.current.screenHeightDp / 2).dp
    val currentMooring = moorings.find { it.leftOn.isEmpty() }
    val initialCurrentLocation =
        if (currentMooring?.latitude != null) {
            Location(currentMooring.latitude, currentMooring.longitude)
        } else {
            Constants.LONDON
        }

    val blanketScaffoldState = rememberBlanketScaffoldState(initialValue = BlanketValue.Collapsed)
    BlanketScaffold(
        frontLayerBackgroundColor = Color.White,
        backLayerBackgroundColor = Color.Gray,
        frontLayerExpandedHeight = frontLayerHeightDp,
        blanketScaffoldState = blanketScaffoldState,
        frontLayerContent = {
            MooringsListContent(
                viewModel,
                moorings,
                onLeftMooringClicked,
                onEditMooringClicked,
                onDeleteMooringClicked,
                onItemClick = {
                    //TODO which action?
                },
                modifier
            )
        },
        backLayerContent = {
            MooringsMapContent(
                viewModel,
                moorings,
                blanketScaffoldState,
                onAddMooringClicked
            )
        }
    )
}


@Preview
@Composable
fun PrevMoorings() {
    MainContent(hiltViewModel(), emptyList(), {}, {}, {}, {})
}