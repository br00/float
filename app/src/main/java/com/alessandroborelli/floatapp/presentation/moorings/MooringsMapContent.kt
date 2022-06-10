package com.alessandroborelli.floatapp.presentation.moorings

import android.content.Context
import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.alessandroborelli.floatapp.R
import com.alessandroborelli.floatapp.domain.model.Location
import com.alessandroborelli.floatapp.domain.model.Mooring
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.maps.android.ui.IconGenerator

@Composable
internal fun MooringsMapContent(viewModel: MooringsViewModel, moorings: List<Mooring>) {

    val location by viewModel.location.collectAsState()

    //TODO val currentLatLng = LatLng(51.53407, -0.04968)
    val currentMooring = moorings.find { it.leftOn.isEmpty() }

    //TODO initial camera position with last mooring
//    val cameraPositionState = rememberCameraPositionState {
//        currentMooring?.let {
//            if (it.latitude != null && it.longitude != null) {
//                position = CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 17f)
//            }
//        }
//    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(location.latitude, location.longitude),17f)
    }

    LaunchedEffect(location) {
        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 17f))
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
                maxZoomPreference = 15f,
                minZoomPreference = 5f,
                mapStyleOptions = mapStyle
            )
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                mapToolbarEnabled = false,
                myLocationButtonEnabled = true,
                zoomControlsEnabled = true
            )
        )
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = mapUiSettings) {

        moorings.forEach { mooring ->
            val mooringLatLng = LatLng(mooring.latitude, mooring.longitude)
            Marker(
                state = MarkerState(position = mooringLatLng),
                title = mooring.displayDate,
                icon = if (mooring.isCurrent) homeIcon(LocalContext.current) else numberIcon(mooring.index, LocalContext.current)
            )
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