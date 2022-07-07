package com.alessandroborelli.floatapp.ui.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.alessandroborelli.floatapp.R
import com.alessandroborelli.floatapp.domain.model.Location
import com.alessandroborelli.floatapp.ui.base.FButtonDefaults
import com.alessandroborelli.floatapp.ui.base.FFilledButton
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.maps.android.ui.IconGenerator

@Composable
fun MapBox(location: Location) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(location.latitude, location.longitude),16f)
    }
    // Set properties using MapProperties which you can use to recompose the map
    val mapStyle = MapStyleOptions.loadRawResourceStyle(LocalContext.current, R.raw.google_map_style)
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true,
                maxZoomPreference = 17f,
                minZoomPreference = 10f,
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
                rotationGesturesEnabled = false,
                scrollGesturesEnabled = false,
                tiltGesturesEnabled = false,
                zoomGesturesEnabled = false,
            )
        )
    }
    Box(modifier = Modifier.background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(size = 8.dp))) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(192.dp),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings
        ) {
            Marker(
                state = MarkerState(position = LatLng(location.latitude, location.longitude)),
                icon = pinIcon(LocalContext.current)
            )
        }
        FFilledButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            colors = FButtonDefaults.filledButtonColorsLight(),
            shape = RoundedCornerShape(50),
            onClick = {}) {
            Icon(
                painterResource(id = R.drawable.ic_edit_location),
                contentDescription = "",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "Edit location")
        }
    }
}

private fun pinIcon(context: Context): BitmapDescriptor {
    val iconFactory = IconGenerator(context)
    iconFactory.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_pin_drop_fill))
    return BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon())
}

@Preview
@Composable
private fun Prev() {
    MapBox(location = Location(51.5377858, -0.0854323))
}