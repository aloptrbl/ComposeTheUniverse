package com.aloptrbl.composekit.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.aloptrbl.composekit.R

@Composable
fun H1(text: String) {
    Text(
        text = text, fontFamily = FontFamily(
            Font(R.font.inter_black)
        ), fontSize = 30.sp
    )
}