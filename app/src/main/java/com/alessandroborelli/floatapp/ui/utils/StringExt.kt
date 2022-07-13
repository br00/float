package com.alessandroborelli.floatapp.ui.utils

/**
 * It returns a string with 2 digits with zeros.
 * E.g. 2 -> 02, 0 -> 00
 */
fun String.convertTo2DigitsZerosFormat(input: Int) =
    if (input in 0..9) {
        "0$input"
    } else {
        input.toString()
    }
