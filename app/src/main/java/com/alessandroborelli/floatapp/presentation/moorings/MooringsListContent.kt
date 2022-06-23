package com.alessandroborelli.floatapp.presentation.moorings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alessandroborelli.floatapp.R
import com.alessandroborelli.floatapp.domain.model.Location
import com.alessandroborelli.floatapp.domain.model.Mooring
import com.alessandroborelli.floatapp.ui.base.FFilledButton
import com.alessandroborelli.floatapp.ui.base.FOutlinedButton

@Composable
internal fun MooringsListContent(
    viewModel: MooringsViewModel,
    moorings: List<Mooring>,
    onLeftMooringClicked: (Mooring) -> Unit,
    onAddMooringClicked: () -> Unit,
    onItemClick: (Mooring) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(16.dp)) {
        LazyColumn() {
            items(
                items = moorings,
                itemContent = { mooring ->
                    MooringItem(
                        item = mooring,
                        onItemClick = {
                            it.latitude.let { latitude ->
                                it.longitude.let { longitude ->
                                    viewModel.onEvent(MooringsUiEvent.UpdateMapLocation(Location(latitude, longitude)))
                                }
                            }
                            onItemClick.invoke(mooring)
                        },
                        onLeftMooringClicked
                    )
                }
            )
        }
        Spacer(modifier.height(8.dp))
        FFilledButton(
            modifier = modifier.padding(start = 16.dp),
            onClick = onAddMooringClicked) {
            Text(text = "Add mooring")
        }
    }
}

@Composable
fun MooringItem(item: Mooring, onItemClick: (Mooring) -> Unit, onLeftMooringClicked: (Mooring) -> Unit) {
    //TODO remove hardcode strings, colors, values
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = { onItemClick(item) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //TODO make a generic circle component
        if (item.isCurrent) {
            Box(contentAlignment= Alignment.Center,
                modifier = Modifier
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
                Icon(
                    painter = painterResource(id = R.drawable.home_pin),
                    tint = colorResource(id = R.color.marker_accent),
                    contentDescription = null // decorative element
                )
            }
        } else {
            val backgroundColor = colorResource(id = R.color.marker)
            Box(contentAlignment= Alignment.Center,
                modifier = Modifier
                    .defaultMinSize(36.dp)
                    .padding(start = 8.dp)
                    .background(backgroundColor, shape = CircleShape)
            ) {
                Text(
                    text = item.index.toString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .padding(4.dp)
                )
            }
        }
        Column(modifier = Modifier
            .padding(16.dp)
            .weight(1f)) {
            Text(
                text = item.displayDate,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = item.displayTime,
                style = MaterialTheme.typography.subtitle2
            )
        }
        if (item.isCurrent) {
            FOutlinedButton(
                modifier = Modifier.padding(top = 4.dp, end = 8.dp),
                onClick = {
                    onLeftMooringClicked.invoke(item)
                }) {
                Text(text = "Leave")
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
        Mooring(
            id = "hvuyf7tunm;l",
            index = 4,
            arrivedOn = "01 Apr (Mon) 09:30",
            leftOn = "",
            creationDate = "",
            lastUpdate = "",
            latitude = 51.53582,
            longitude = -0.06652,
            name = "3")
    )

    //MooringItem(item = moorings.last(), onItemClick = {}, onLeftMooringClicked = {})

    MooringsListContent(
        hiltViewModel(),
        moorings,
        {},
        {},
        {}
    )
}