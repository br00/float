package com.alessandroborelli.floatapp.presentation.moorings

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.alessandroborelli.floatapp.R
import com.alessandroborelli.floatapp.domain.model.Mooring
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.maps.android.ui.IconGenerator

@Composable
fun MooringsMapContent(moorings: List<Mooring>) {

    //TODO val currentLatLng = LatLng(51.53407, -0.04968)
    val currentMooring = moorings.find { it.leftOn.isEmpty() }
    val cameraPositionState = rememberCameraPositionState {
        currentMooring?.let {
            if (it.latitude != null && it.longitude != null) {
                position = CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 17f)
            }
        }
    }
    // Set properties using MapProperties which you can use to recompose the map
    val mapStyle = MapStyleOptions.loadRawResourceStyle(LocalContext.current, R.raw.google_map_style)
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoomPreference = 15f,
                minZoomPreference = 5f,
                mapStyleOptions = mapStyle
            )
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(mapToolbarEnabled = false, zoomControlsEnabled = true)
        )
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings) {

            moorings.forEach { mooring ->
                val mooringLatLng = LatLng(mooring.latitude, mooring.longitude)
                Marker(
                    state = MarkerState(position = mooringLatLng),
                    title = mooring.index.toString(),
                    icon = numberIcon(mooring.index, LocalContext.current)
                )
            }

        }
    }
}

private fun numberIcon(number: Int, context: Context): BitmapDescriptor {
    val iconFactory = IconGenerator(context)
    iconFactory.setTextAppearance(R.style.MarkerTextStyle)
    iconFactory.setBackground(ContextCompat.getDrawable(context, R.drawable.marker_background))
    return BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(number.toString()))
}

@Preview
@Composable
fun PrevMooringsMap() {
    MooringsMapContent(emptyList())
}