package com.alessandroborelli.floatapp.presentation.moorings

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AddLocation
import androidx.compose.material.icons.outlined.Fullscreen
import androidx.compose.material.icons.outlined.FullscreenExit
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.alessandroborelli.floatapp.R
import com.alessandroborelli.floatapp.domain.model.Location
import com.alessandroborelli.floatapp.domain.model.Mooring
import com.alessandroborelli.floatapp.ui.base.BlanketScaffoldState
import com.alessandroborelli.floatapp.ui.utils.fusedLocationWrapper
import com.alessandroborelli.floatapp.ui.utils.mirroringBackIcon
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.maps.android.ui.IconGenerator
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@Composable
internal fun MooringsMapContent(
    viewModel: MooringsViewModel,
    moorings: List<Mooring>,
    blanketScaffoldState: BlanketScaffoldState,
    onAddMooringClicked: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState()
    val location = state.value.selectedMapLocation
    val context = LocalContext.current
    val fusedLocationWrapper = context.fusedLocationWrapper()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(location.latitude, location.longitude),17f)
    }

    LaunchedEffect(location) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 17f))
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        val position = cameraPositionState.position
        if (!cameraPositionState.isMoving) {
            val cameraLocation = Location(position.target.latitude, position.target.longitude)
            viewModel.setCameraPosition(cameraLocation)
        }
    }

    // Set properties using MapProperties which you can use to recompose the map
    val mapStyle = MapStyleOptions.loadRawResourceStyle(LocalContext.current, R.raw.google_map_style)
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true,
                maxZoomPreference = 18f,
                minZoomPreference = 5f,
                mapStyleOptions = mapStyle
            )
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                mapToolbarEnabled = false,
                myLocationButtonEnabled = false,
                zoomControlsEnabled = false,
            )
        )
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings,
            contentPadding = PaddingValues(bottom = 36.dp, start = 8.dp, end = 8.dp, top = 8.dp)
        ) {
            moorings.forEach { mooring ->
                val mooringLatLng = LatLng(mooring.latitude, mooring.longitude)
                Marker(
                    state = MarkerState(position = mooringLatLng),
                    title = mooring.displayDate,
                    icon = if (mooring.isCurrent) homeIcon(LocalContext.current) else numberIcon(
                        mooring.index,
                        LocalContext.current
                    )
                )
            }
        }
        Column(modifier = Modifier.align(Alignment.TopStart)) {
            //TODO make button component
            IconButton(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(30))
                    .border(width = 1.dp, color = MaterialTheme.colors.background)
                    .size(36.dp)
                    .background(
                        color = MaterialTheme.colors.surface.copy(alpha = 0.87f),
                        shape = RoundedCornerShape(30),
                    ),
                onClick = {
                    scope.launch {
                        val userLocation = fusedLocationWrapper.awaitLastLocation()
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    userLocation.latitude,
                                    userLocation.longitude
                                ), 17f
                            )
                        )
                    }
                }
            ) {
                Icon(imageVector = Icons.Outlined.MyLocation, contentDescription = null, tint = MaterialTheme.colors.onBackground)
            }
            IconButton(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(30))
                    .border(width = 1.dp, color = MaterialTheme.colors.background)
                    .size(36.dp)
                    .background(
                        color = MaterialTheme.colors.surface.copy(alpha = 0.87f),
                        shape = RoundedCornerShape(30),
                    ),
                onClick = {
                    scope.launch {
                        if (blanketScaffoldState.isCollapsed) {
                            blanketScaffoldState.expand()
                        } else {
                            blanketScaffoldState.collapse()
                        }
                    }
                }
            ) {
                val imageVector = if (blanketScaffoldState.isCollapsed) Icons.Outlined.FullscreenExit else Icons.Outlined.Fullscreen
                Icon(imageVector = imageVector, contentDescription = null, tint = MaterialTheme.colors.onBackground)
            }
        }
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 48.dp)
                .background(color = MaterialTheme.colors.secondary, shape = CircleShape)
                .size(48.dp),
            onClick = onAddMooringClicked
        ) {
            Icon(imageVector = Icons.Outlined.AddLocation, contentDescription = null, tint = MaterialTheme.colors.onSecondary)
        }
    }

}

private fun numberIcon(number: Int, context: Context): BitmapDescriptor {
    val iconFactory = IconGenerator(context)
    iconFactory.setTextAppearance(R.style.MarkerTextStyle)
    iconFactory.setBackground(ContextCompat.getDrawable(context, R.drawable.marker_background))
    return BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(number.toString()))
}

private fun homeIcon(context: Context): BitmapDescriptor {
    val iconFactory = IconGenerator(context)
    iconFactory.setBackground(ContextCompat.getDrawable(context, R.drawable.home_pin))
    return BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon())
}

@Preview
@Composable
fun PrevMooringsMap() {
//TODO
//MooringsMapContent(emptyList())
}