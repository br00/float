package com.alessandroborelli.floatapp.presentation.moorings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alessandroborelli.floatapp.ui.utils.mirroringBackIcon
import com.alessandroborelli.floatapp.R
import com.alessandroborelli.floatapp.data.Constants
import com.alessandroborelli.floatapp.domain.model.Location
import com.alessandroborelli.floatapp.ui.base.FOutlinedDateField
import com.alessandroborelli.floatapp.ui.base.FOutlinedTextField
import com.alessandroborelli.floatapp.ui.components.MapBox
import com.alessandroborelli.floatapp.ui.theme.FloatTheme
import com.alessandroborelli.floatapp.ui.utils.fusedLocationWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
internal fun AddNewMooringScreen(upPress: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Content()
        Up(upPress, Modifier.align(Alignment.TopStart))
        Save({}, Modifier.align(Alignment.TopEnd)) //TODO change callback
    }
}

@Composable
private fun Up(upPress: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = upPress,
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .size(36.dp)
            .background(
                color = MaterialTheme.colors.secondary.copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = mirroringBackIcon(),
            tint = MaterialTheme.colors.onSecondary,
            contentDescription = stringResource(R.string.label_back)
        )
    }
}

@Composable
private fun Save(savePress: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = savePress,
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .size(36.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Check,
            tint = MaterialTheme.colors.secondary,
            contentDescription = stringResource(R.string.label_save)
        )
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("MissingPermission")
@Composable
private fun Content() {
    var description by remember { mutableStateOf("") }
    var arrivedDate by remember { mutableStateOf("") }
    var leftDate by remember { mutableStateOf("") }

    val context = LocalContext.current
    val fusedLocationWrapper = context.fusedLocationWrapper()
    val location: MutableState<Location?> = remember {
        mutableStateOf(null)
    }
    LaunchedEffect(fusedLocationWrapper) {
        val userLocation = fusedLocationWrapper.awaitLastLocation()
        location.value = Location(userLocation.latitude, userLocation.longitude)
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "Add mooring",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(36.dp))
            Text(text = "Mooring details", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(16.dp))
            FOutlinedTextField(
                text = description,
                onChange = { description = it },
                label = "description",
                isMultiLines = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            FOutlinedDateField(
                label = "arrived on",
                onChange = { arrivedDate = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            FOutlinedDateField(
                label = "left on",
                onChange = { leftDate = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (location.value != null) {
                location.value?.let {
                    MapBox(location = it)
                }
            }
        }
    }
}

@Preview
@Composable
private fun prev() {
    FloatTheme {
        AddNewMooringScreen(upPress = {})
    }
}