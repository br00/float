package com.alessandroborelli.floatapp.ui.base

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alessandroborelli.floatapp.ui.theme.FloatTheme
import com.alessandroborelli.floatapp.ui.utils.convertTo2DigitsZerosFormat
import java.util.*

@Composable
fun FOutlinedTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String = "",
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyBoardActions: KeyboardActions = KeyboardActions(),
    isEnabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    isMultiLines: Boolean = false
) {
    val lines = if (isMultiLines) 3 else 1
    val textSize = MaterialTheme.typography.body1.fontSize
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = onChange,
        leadingIcon = leadingIcon,
        textStyle = TextStyle(fontSize = textSize),
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        keyboardActions = keyBoardActions,
        maxLines = lines,
        enabled = isEnabled,
        readOnly = readOnly,
        isError = isError,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.primaryVariant,
            disabledBorderColor = MaterialTheme.colors.primaryVariant,
            disabledTextColor = MaterialTheme.colors.onPrimary //TODO change this for date picker only
        ),
        placeholder = {
            Text(text = placeholder, style = TextStyle(fontSize = textSize, color = MaterialTheme.colors.onPrimary))
        },
        label = {
            Text(
                text = label,
                style = TextStyle(fontSize = textSize, color = MaterialTheme.colors.onPrimary)
            )
        }
    )
}

@Composable
fun FOutlinedDateField(
    text: String,
    label: String,
    isError: Boolean = false,
    onChange: (String) -> Unit,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, y: Int, m: Int, dayOfMonth: Int ->
            val realMonth = m+1
            onDateSelected("$dayOfMonth/$realMonth/$y")
        }, year, month, day
    )
    datePickerDialog.datePicker.maxDate = calendar.timeInMillis
    val buttonColor = MaterialTheme.colors.secondary.toArgb()
    FOutlinedTextField(
        modifier = modifier.clickable {
            datePickerDialog.show()
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(buttonColor)
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(buttonColor)
                                      },
        text = text,
        onChange = onChange,
        label = label,
        readOnly = true,
        isEnabled = false,
        isError = isError
    )
}

@Composable
fun FOutlinedTimeField(
    text: String,
    label: String,
    isError: Boolean = false,
    onChange: (String) -> Unit = {},
    onTimeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val hour: Int
    val minute: Int
    val calendar = Calendar.getInstance()
    hour = calendar.get(Calendar.HOUR_OF_DAY)
    minute = calendar.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minuteOfDay -> onTimeSelected("$hourOfDay:"+String().convertTo2DigitsZerosFormat(minuteOfDay)) },
        hour,
        minute,
        true
    )
    val buttonColor = MaterialTheme.colors.secondary.toArgb()
    FOutlinedTextField(
        modifier = modifier.clickable {
            timePickerDialog.show()
            timePickerDialog.getButton(TimePickerDialog.BUTTON_NEGATIVE).setTextColor(buttonColor)
            timePickerDialog.getButton(TimePickerDialog.BUTTON_POSITIVE).setTextColor(buttonColor)
        },
        text = text,
        onChange = onChange,
        label = label,
        readOnly = true,
        isEnabled = false,
        isError = isError
    )
}

@Preview
@Composable
private fun Prev() {
    FloatTheme {
        Surface {
            Column() {
                FOutlinedTextField(
                    modifier = Modifier.height(80.dp),
                    text = "ciao",
                    onChange = {},
                    label = "description",
                    isError = false
                )
            }
        }
    }
}
