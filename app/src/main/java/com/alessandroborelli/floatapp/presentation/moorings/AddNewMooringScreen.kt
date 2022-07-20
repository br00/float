package com.alessandroborelli.floatapp.presentation.moorings

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alessandroborelli.floatapp.ui.utils.mirroringBackIcon
import com.alessandroborelli.floatapp.R
import com.alessandroborelli.floatapp.data.Constants.INVALID_LAT_LNG
import com.alessandroborelli.floatapp.data.Constants.UNDEFINED_INDEX
import com.alessandroborelli.floatapp.domain.model.Location
import com.alessandroborelli.floatapp.domain.model.Mooring
import com.alessandroborelli.floatapp.ui.base.FOutlinedDateField
import com.alessandroborelli.floatapp.ui.base.FOutlinedTextField
import com.alessandroborelli.floatapp.ui.base.FOutlinedTimeField
import com.alessandroborelli.floatapp.ui.components.MapBox
import com.alessandroborelli.floatapp.ui.theme.FloatTheme
import com.alessandroborelli.floatapp.ui.utils.fusedLocationWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
internal fun AddNewMooringScreen(viewModel: MooringsViewModel, upPress: () -> Unit) {
    val mooringState: MutableState<Mooring> = remember {
        mutableStateOf(Mooring("",UNDEFINED_INDEX,"","","","",INVALID_LAT_LNG,INVALID_LAT_LNG,"", ""))
    }
    Box(Modifier.fillMaxSize()) {
        Content(mooringState.value, onMooringUpdated = {
            mooringState.value = it
        })
        Up(upPress, Modifier.align(Alignment.TopStart))
        Save({
            val mooring = mooringState.value
            if (mooring.isValid()) {
                upPress.invoke()
                viewModel.onEvent(MooringsUiEvent.AddMooring(mooring))
            }
        }, Modifier.align(Alignment.TopEnd)) //TODO change callback
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
    ) {
        Icon(
            imageVector = Icons.Outlined.Close,
            tint = MaterialTheme.colors.onBackground,
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
private fun Content(mooring: Mooring, onMooringUpdated: (Mooring) -> Unit) {

    var notesTextError by remember { mutableStateOf(false) }
    var arriveDateTextError by remember { mutableStateOf(false) }
    var arriveTimeTextError by remember { mutableStateOf(false) }
    var leftDateTextError by remember { mutableStateOf(false) }
    var leftTimeTextError by remember { mutableStateOf(false) }

    var notes by remember { mutableStateOf("") }
    var arrivedDate by remember { mutableStateOf("") }
    var arrivedTime by remember { mutableStateOf("") }
    var leftDate by remember { mutableStateOf("") }
    var leftTime by remember { mutableStateOf("") }


    val notesUpdates = { data : String ->
        notesTextError = data.isEmpty()
        onMooringUpdated(mooring.copy(notes = data))
        notes = data
    }
    val arriveDateUpdates = { data : String ->
        arriveDateTextError = data.isEmpty()
        onMooringUpdated(mooring.copy(arrivedOn = "$data $arrivedTime"))
        arrivedDate = data
    }
    val arriveTimeUpdates = { data : String ->
        arriveTimeTextError = data.isEmpty()
        onMooringUpdated(mooring.copy(arrivedOn = "$arrivedDate $data"))
        arrivedTime = data
    }
    val leftDateUpdates = { data : String ->
        leftDateTextError = data.isEmpty()
        onMooringUpdated(mooring.copy(leftOn = "$data $leftTime"))
        leftDate = data
    }
    val leftTimeUpdates = { data : String ->
        leftTimeTextError = data.isEmpty()
        onMooringUpdated(mooring.copy(leftOn = "$leftDate $data"))
        leftTime = data
    }

    val context = LocalContext.current
    val fusedLocationWrapper = context.fusedLocationWrapper()
    val location: MutableState<Location?> = remember {
        mutableStateOf(null)
    }
    LaunchedEffect(fusedLocationWrapper) {
        val userLocation = fusedLocationWrapper.awaitLastLocation()
        onMooringUpdated(mooring.copy(latitude = userLocation.latitude, longitude = userLocation.longitude))
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
                text = notes,
                onChange = notesUpdates,
                label = "notes",
                isMultiLines = true,
                isError = notesTextError
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                FOutlinedDateField(
                    text = arrivedDate,
                    label = "arrived on",
                    isError = arriveDateTextError,
                    onDateSelected = arriveDateUpdates,
                    onChange = arriveDateUpdates,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                )
                FOutlinedTimeField(
                    text = arrivedTime,
                    label = "at",
                    isError = arriveTimeTextError,
                    onTimeSelected = arriveTimeUpdates,
                    onChange = { arrivedTime = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                FOutlinedDateField(
                    text = leftDate,
                    label = "left on",
                    isError = leftDateTextError,
                    onDateSelected = leftDateUpdates,
                    onChange = leftDateUpdates,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                )
                FOutlinedTimeField(
                    text = leftTime,
                    label = "at",
                    isError = leftTimeTextError,
                    onTimeSelected = leftTimeUpdates,
                    onChange = leftTimeUpdates,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                )
            }
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
        AddNewMooringScreen(hiltViewModel(), upPress = {})
    }
}