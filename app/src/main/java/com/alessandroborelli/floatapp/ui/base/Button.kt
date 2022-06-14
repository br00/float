package com.alessandroborelli.floatapp.ui.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alessandroborelli.floatapp.ui.theme.FloatTheme

/**
 * Custom button to make the colors right.
 * F is for 'Float'
 */

@Composable
fun FFilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = FButtonDefaults.filledButtonColors(),
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        content = {
            content()
        }
    )
}

@Composable
fun FOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    border: BorderStroke? = FButtonDefaults.outlinedButtonBorder(enabled = enabled),
    colors: ButtonColors = FButtonDefaults.outlinedButtonColors(),
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        border = border,
        colors = colors,
        content = {
            content()
        }
    )
}

@Preview
@Composable
fun Prev() {
    FloatTheme {
        Surface {
            Column() {
                FFilledButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = {}) {
                    Text(text = "Add mooring")
                }
                FOutlinedButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = {}) {
                    Text(text = "Leave")
                }
            }
        }
    }
}

object FButtonDefaults {
    const val DisabledButtonContainerAlpha = 0.12f
    const val DisabledButtonContentAlpha = 0.38f
    @Composable
    fun filledButtonColors(
        containerColor: Color = MaterialTheme.colors.secondary,
        contentColor: Color = MaterialTheme.colors.onSecondary,
        disabledContainerColor: Color = MaterialTheme.colors.secondary.copy(
            alpha = DisabledButtonContainerAlpha
        ),
        disabledContentColor: Color = MaterialTheme.colors.onSecondary.copy(
            alpha = DisabledButtonContentAlpha
        )
    ) = ButtonDefaults.buttonColors(
        backgroundColor = containerColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )
    @Composable
    fun outlinedButtonBorder(
        enabled: Boolean,
        width: Dp = 1.dp,
        color: Color = MaterialTheme.colors.background,
        disabledColor: Color = MaterialTheme.colors.background.copy(
            alpha = DisabledButtonContainerAlpha
        )
    ): BorderStroke = BorderStroke(
        width = width,
        color = if (enabled) color else disabledColor
    )
    @Composable
    fun outlinedButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = MaterialTheme.colors.secondary,
        disabledContentColor: Color = MaterialTheme.colors.secondary.copy(
            alpha = DisabledButtonContentAlpha
        )
    ) = ButtonDefaults.outlinedButtonColors(
        backgroundColor = containerColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor
    )
}