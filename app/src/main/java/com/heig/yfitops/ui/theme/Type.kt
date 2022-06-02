package com.heig.yfitops.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.heig.yfitops.R


val Roboto_Regular = FontFamily(
    Font(R.font.roboto_regular)
)
val Courgette_Regular = FontFamily(
    Font(R.font.courgette_regular),
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Courgette_Regular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Roboto_Regular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )


)