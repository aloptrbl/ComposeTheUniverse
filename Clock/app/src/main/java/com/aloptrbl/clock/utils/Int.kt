package com.aloptrbl.clock.utils

fun Int.toRoman(): String {
    val map = listOf(
        1000 to "M",
        900 to "CM",
        500 to "D",
        400 to "CD",
        100 to "C",
        90 to "XC",
        50 to "L",
        40 to "XL",
        10 to "X",
        9 to "IX",
        5 to "V",
        4 to "IV",
        1 to "I"
    )

    var num = this
    val result = StringBuilder()

    while (num > 0) {
        for ((value, symbol) in map) {
            while (num >= value) {
                num -= value
                result.append(symbol)
            }
        }
    }

    return result.toString()
}