package com.alessandroborelli.floatapp.presentation.moorings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alessandroborelli.floatapp.R
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

@Composable
fun MainContent(moorings: List<Mooring>, onLeftMooringClicked: (Mooring) -> Unit, onAddMooringClicked: () -> Unit) {
    Column() {
        LazyColumn() {
            items(
                items = moorings,
                itemContent = { mooring ->
                    MooringItem(item = mooring, onLeftMooringClicked)
                }
            )
        }
        Spacer(Modifier.height(8.dp))
        Button(
            modifier = Modifier.padding(start = 16.dp),
            onClick = onAddMooringClicked) {
            Text(text = "Add mooring")
        }
    }
}

@Composable
fun MooringItem(item: Mooring, onLeftMooringClicked: (Mooring) -> Unit) {
    //TODO remove hardcode strings, colors, values
    val isCurrentMooring = item.leftOn.isEmpty()
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //TODO make a generic circle component
        val backgroundColor = if (isCurrentMooring) Color.Black else Color.Gray
        Box(contentAlignment= Alignment.Center,
            modifier = Modifier
                .background(backgroundColor, shape = CircleShape)
                .layout() { measurable, constraints ->
                    // Measure the composable
                    val placeable = measurable.measure(constraints)

                    //get the current max dimension to assign width=height
                    val currentHeight = placeable.height
                    var heightCircle = currentHeight
                    if (placeable.width > heightCircle)
                        heightCircle = placeable.width

                    //assign the dimension and the center position
                    layout(heightCircle, heightCircle) {
                        // Where the composable gets placed
                        placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                    }
                }
        ) {
            Text(
                text = item.index.toString(),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .padding(4.dp)
                    .defaultMinSize(24.dp)
            )
        }
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = "arrived on: ${item.arrivedOn}",
                style = MaterialTheme.typography.subtitle1
            )
            if (!isCurrentMooring) {
                Text(
                    text = "left on: ${item.leftOn}",
                    style = MaterialTheme.typography.subtitle1
                )
            } else {
                OutlinedButton(
                    modifier = Modifier.padding(top = 4.dp),
                    onClick = {
                        onLeftMooringClicked.invoke(item)
                    }) {
                    Text(text = "Leave")
                }
            }
        }
    }
}

@Preview
@Composable
fun PrevMooringItem() {
    val mooring = Mooring(
        id = "hvuyf79hnm;l",
        index = 1,
        arrivedOn = "22 Jan (Tue) 12:30",
        leftOn = "30 Jan (Tue) 09:30",
        creationDate = "",
        lastUpdate = "",
        latitude = "51.53582",
        longitude = "-0.06652",
        name = "3"
    )
    MooringItem(item = mooring, {})
}