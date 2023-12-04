package com.aloptrbl.composekit.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aloptrbl.composekit.R

// Set of Material typography styles to start with
var Inter = FontFamily(
    Font(R.font.inter),
)

var Typography = Typography(
    displayLarge = Typography().displayLarge.copy(fontFamily = Inter),
    displayMedium = Typography().displayMedium.copy(fontFamily = Inter),
    displaySmall = Typography().displaySmall.copy(fontFamily = Inter),
    headlineLarge = Typography().headlineLarge.copy(fontFamily = Inter),
    headlineMedium = Typography().headlineMedium.copy(fontFamily = Inter),
    headlineSmall = Typography().headlineSmall.copy(fontFamily = Inter),
    titleLarge = Typography().titleLarge.copy(fontFamily = Inter),
    titleMedium = Typography().titleMedium.copy(fontFamily = Inter),
    titleSmall = Typography().titleSmall.copy(fontFamily = Inter),
    bodyLarge = Typography().bodyLarge.copy(fontFamily = Inter),
    bodyMedium = Typography().bodyMedium.copy(fontFamily = Inter),
    bodySmall = Typography().bodySmall.copy(fontFamily = Inter),
    labelLarge = Typography().labelLarge.copy(fontFamily = Inter),
    labelMedium = Typography().labelMedium.copy(fontFamily = Inter),
    labelSmall = Typography().labelSmall.copy(fontFamily = Inter)
)