package com.alessandroborelli.floatapp.ui.base

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Stable
class BlanketScaffoldState(
    initialValue: BlanketValue
) {
    var currentValue by mutableStateOf(initialValue)

    val isExtended: Boolean
        get() = currentValue == BlanketValue.Expanded

    val isCollapsed: Boolean
        get() = currentValue == BlanketValue.Collapsed

    fun expand() {
        currentValue = BlanketValue.Expanded
    }

    fun collapse() {
        currentValue = BlanketValue.Collapsed
    }

    companion object {
        fun Saver(): Saver<BlanketScaffoldState, *> = Saver(
            save = { it.currentValue },
            restore = {
                BlanketScaffoldState(
                    initialValue = it
                )
            }
        )
    }
}

@Composable
@ExperimentalMaterialApi
fun rememberBlanketScaffoldState(
    initialValue: BlanketValue
): BlanketScaffoldState {
    return rememberSaveable(
        saver = BlanketScaffoldState.Saver()
    ) {
        BlanketScaffoldState(
            initialValue = initialValue,
        )
    }
}

enum class BlanketValue {
    Expanded,
    Collapsed
}

const val BLANKER_CORNER_SHAPE_SIZE = 20

val BlanketShape = RoundedCornerShape(
    topStart = BLANKER_CORNER_SHAPE_SIZE.dp,
    topEnd = BLANKER_CORNER_SHAPE_SIZE.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)

@Composable
@ExperimentalMaterialApi
fun BlanketScaffold(
    frontLayerBackgroundColor: Color,
    backLayerBackgroundColor: Color,
    frontLayerExpandedHeight: Dp = 400.dp,
    frontLayerCollapsedHeight: Dp = 200.dp,
    blanketScaffoldState: BlanketScaffoldState = rememberBlanketScaffoldState(initialValue = BlanketValue.Collapsed),
    frontLayerContent: @Composable () -> Unit,
    backLayerContent: @Composable () -> Unit
) {
    val backLayerCollapsedHeight = (LocalConfiguration.current.screenHeightDp - frontLayerExpandedHeight.value - BLANKER_CORNER_SHAPE_SIZE).dp
    val backLayerExpandedHeight = (LocalConfiguration.current.screenHeightDp - frontLayerCollapsedHeight.value - BLANKER_CORNER_SHAPE_SIZE).dp
    val animatedFrontLayerHeightDp: Dp by animateDpAsState(targetValue = if (blanketScaffoldState.isCollapsed) frontLayerCollapsedHeight else frontLayerExpandedHeight)
    val animatedBackLayerHeightDp: Dp by animateDpAsState(targetValue = if (blanketScaffoldState.isCollapsed) backLayerExpandedHeight else backLayerCollapsedHeight)

    Surface(color = backLayerBackgroundColor) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight().padding(bottom = 40.dp)) {

            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .height(animatedBackLayerHeightDp)
            ) {
                backLayerContent()
            }
            Surface(
                color = frontLayerBackgroundColor,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(animatedFrontLayerHeightDp),
                shape = BlanketShape) {
                frontLayerContent()
            }
        }
    }
}