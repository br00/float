package com.alessandroborelli.floatapp.presentation.moorings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alessandroborelli.floatapp.domain.model.Mooring
import com.alessandroborelli.floatapp.ui.theme.BottomSheetShape

@Composable
fun MooringsListContent(
    moorings: List<Mooring>,
    onLeftMooringClicked: (Mooring) -> Unit,
    onAddMooringClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize(), color = Color.White, shape = BottomSheetShape) {
        Column(modifier.padding(16.dp)) {
            LazyColumn() {
                items(
                    items = moorings,
                    itemContent = { mooring ->
                        MooringItem(item = mooring, onLeftMooringClicked)
                    }
                )
            }
            Spacer(modifier.height(8.dp))
            Button(
                modifier = modifier.padding(start = 16.dp),
                onClick = onAddMooringClicked) {
                Text(text = "Add mooring")
            }
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
fun PrevMooringsList() {
    val moorings = listOf(
        Mooring(
            id = "hvuyet9hnm;l",
            index = 1,
            arrivedOn = "22 Jan (Tue) 12:30",
            leftOn = "30 Jan (Tue) 09:30",
            creationDate = "",
            lastUpdate = "",
            latitude = 51.53582,
            longitude = -0.06652,
            name = "1"),
        Mooring(
            id = "hvuyf79hnm;l",
            index = 2,
            arrivedOn = "12 Mar (Tue) 09:30",
            leftOn = "20 Mar (Tue) 19:00",
            creationDate = "",
            lastUpdate = "",
            latitude = 51.53582,
            longitude = -0.06652,
            name = "2"),
        Mooring(
            id = "hvuyf7tunm;l",
            index = 3,
            arrivedOn = "22 Mar (Mon) 11:30",
            leftOn = "24 Mar (Tue) 12:30",
            creationDate = "",
            lastUpdate = "",
            latitude = 51.53582,
            longitude = -0.06652,
            name = "3"),
    )

    MooringsListContent(
        moorings,
        {},
        {}
    )
}