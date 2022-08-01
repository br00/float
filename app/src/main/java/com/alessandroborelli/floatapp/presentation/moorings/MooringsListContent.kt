package com.alessandroborelli.floatapp.presentation.moorings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.StickyNote2
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alessandroborelli.floatapp.R
import com.alessandroborelli.floatapp.domain.model.Location
import com.alessandroborelli.floatapp.domain.model.Mooring
import com.alessandroborelli.floatapp.ui.base.FFilledButton
import com.alessandroborelli.floatapp.ui.base.FOutlinedButton
import com.alessandroborelli.floatapp.ui.theme.FloatTheme

@Composable
internal fun MooringsListContent(
    viewModel: MooringsViewModel,
    moorings: List<Mooring>,
    onLeftMooringClicked: (Mooring) -> Unit,
    onEditMooringClicked: (Mooring) -> Unit,
    onDeleteMooringClicked: (Mooring) -> Unit,
    onItemClick: (Mooring) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(16.dp)) {
        LazyColumn(modifier.padding(top = 16.dp), contentPadding = PaddingValues(bottom = 48.dp)) {
            itemsIndexed(moorings) { index, item ->
                MooringItem(
                    index = index,
                    listSize = moorings.size,
                    item = item,
                    onItemClick = {
                        it.latitude.let { latitude ->
                            it.longitude.let { longitude ->
                                viewModel.onEvent(MooringsUiEvent.UpdateMapLocation(Location(latitude, longitude)))
                            }
                        }
                        onItemClick.invoke(item)
                    },
                    onLeftMooringClicked = onLeftMooringClicked,
                    onEditActionClicked = onEditMooringClicked,
                    onDeleteActionClicked = onDeleteMooringClicked
                )
            }
        }
    }
}

@Composable
fun MooringItem(
    index: Int,
    listSize: Int,
    item: Mooring,
    onItemClick: (Mooring) -> Unit,
    onLeftMooringClicked: (Mooring) -> Unit,
    onEditActionClicked: (Mooring) -> Unit,
    onDeleteActionClicked: (Mooring) -> Unit,
) {
    //TODO remove hardcode strings, colors, values
    var isMoreActionsMenuShown by remember { mutableStateOf(false) }
    val isLastItem by remember { mutableStateOf(index == listSize-1) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(start = 8.dp, end = 8.dp)
            .clickable(onClick = { onItemClick(item) }),
        verticalAlignment = Alignment.Top
    ) {
        //TODO make a generic circle component
        if (item.isCurrent) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 8.dp)) {
                Box(contentAlignment= Alignment.TopCenter,
                    modifier = Modifier
                        .defaultMinSize(48.dp)
                        .padding(bottom = 8.dp)
                        .background(shape = CircleShape, color = Color.Transparent)
                ) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        painter = painterResource(id = R.drawable.home_pin),
                        tint = colorResource(id = R.color.marker_accent),
                        contentDescription = null // decorative element
                    )
                }
                if (!isLastItem) {
                    Box(
                        modifier = Modifier
                            .background(color = MaterialTheme.colors.onBackground)
                            .width(2.dp)
                            .weight(1f)
                    )
                }
            }

        } else {
            val backgroundColor = colorResource(id = R.color.marker)
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 8.dp)) {
                Box(contentAlignment= Alignment.TopCenter,
                    modifier = Modifier
                        .defaultMinSize(48.dp)
                        .padding(start = 10.dp, bottom = 10.dp, end = 10.dp, top = 4.dp)
                        .background(backgroundColor, shape = CircleShape)
                ) {
                    Text(
                        text = (index+1).toString(),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .padding(3.dp)
                    )
                }
                if (!isLastItem) {
                    Box(
                        modifier = Modifier
                            .background(color = MaterialTheme.colors.onBackground)
                            .width(2.dp)
                            .weight(1f)
                    )
                }
            }
        }
        Column(modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp, top = 4.dp)
            .weight(1f)) {
            Text(
                modifier = Modifier.padding(start = 2.dp, bottom = 4.dp), // to compensate the icon internal padding
                text = item.displayDate,
                style = MaterialTheme.typography.subtitle1
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground)
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = item.displayTime,
                    style = MaterialTheme.typography.body2
                )
            }
            if (item.notes.isNotEmpty()) {
                Row(modifier = Modifier.padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.StickyNote2,
                        contentDescription = "",
                        tint = MaterialTheme.colors.onBackground
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = item.notes,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
        if (item.isCurrent) {
            FOutlinedButton(
                modifier = Modifier.padding(top = 4.dp, end = 8.dp),
                onClick = { onLeftMooringClicked(item) }) {
                Text(text = "Leave")
            }
        }
        Box {
            IconButton(modifier = Modifier.padding(8.dp), onClick = { isMoreActionsMenuShown = true }) {
                Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                expanded = isMoreActionsMenuShown,
                onDismissRequest = { isMoreActionsMenuShown = false }) {
                DropdownMenuItem(onClick = { onEditActionClicked(item) }) {
                    Text("Edit")
                }
                DropdownMenuItem(onClick = { onDeleteActionClicked(item) }) {
                    Text("Delete")
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
            name = "1",
            notes = "engine broken down"),
        Mooring(
            id = "hvuyf79hnm;l",
            index = 2,
            arrivedOn = "12 Mar (Tue) 09:30",
            leftOn = "20 Mar (Tue) 19:00",
            creationDate = "",
            lastUpdate = "",
            latitude = 51.53582,
            longitude = -0.06652,
            name = "2",
            notes = ""),
        Mooring(
            id = "hvuyf7tunm;l",
            index = 3,
            arrivedOn = "22 Mar (Mon) 11:30",
            leftOn = "24 Mar (Tue) 12:30",
            creationDate = "",
            lastUpdate = "",
            latitude = 51.53582,
            longitude = -0.06652,
            name = "3",
            notes = ""),
        Mooring(
            id = "hvuyf7tunm;l",
            index = 4,
            arrivedOn = "01 Apr (Mon) 09:30",
            leftOn = "",
            creationDate = "",
            lastUpdate = "",
            latitude = 51.53582,
            longitude = -0.06652,
            name = "3",
            notes = "")
    )

    FloatTheme {
        Surface {
            Column {
                MooringItem(index = 1, listSize = moorings.size, item = moorings.last(), {}, {}, {}, {})
                MooringItem(index = 2, listSize = moorings.size, item = moorings.first(), {}, {}, {}, {})
                MooringItem(index = 3, listSize = moorings.size, item = moorings[1], {}, {}, {}, {})
            }
        }
    }

//    MooringsListContent(
//        hiltViewModel(),
//        moorings,
//        {},
//        {},
//        {}
//    )
}