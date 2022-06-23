package com.alessandroborelli.floatapp.presentation.moorings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alessandroborelli.floatapp.ui.utils.mirroringBackIcon
import com.alessandroborelli.floatapp.R
import com.alessandroborelli.floatapp.ui.theme.FloatTheme

@Composable
internal fun AddNewMooringScreen(upPress: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Up(upPress)
        Content()
    }
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
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
private fun Content() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "New mooring",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun prev() {
    FloatTheme {
        AddNewMooringScreen(upPress = {})
    }
}