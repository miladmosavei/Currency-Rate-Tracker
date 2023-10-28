package com.example.currentrack.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.currentrack.R

val satoshi_black = FontFamily(
    Font(R.font.satoshi_black)
)
val satoshi_black_italic = FontFamily(
    Font(R.font.satoshi_black_italic)
)
val satoshi_bold = FontFamily(
    Font(R.font.satoshi_bold)
)
val satoshi_bold_italic = FontFamily(
    Font(R.font.satoshi_bold_italic)
)
val satoshi_italic = FontFamily(
    Font(R.font.satoshi_italic)
)
val satoshi_light = FontFamily(
    Font(R.font.satoshi_light)
)
val satoshi_light_italic = FontFamily(
    Font(R.font.satoshi_light_italic)
)

val satoshi_medium = FontFamily(
    Font(R.font.satoshi_medium)
)
val satoshi_medium_italic = FontFamily(
    Font(R.font.satoshi_medium_italic)
)
val satoshi_regular = FontFamily(
    Font(R.font.satoshi_regular)
)


val Typography = Typography(
    h1 = TextStyle(
        fontSize = FONT_SIZE_H1,
        fontWeight = FontWeight.Bold,
        fontFamily = satoshi_bold
    ),
    h5 = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = satoshi_bold
    ),
    caption = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = satoshi_medium
    ),
    h6 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = satoshi_regular
    )

)