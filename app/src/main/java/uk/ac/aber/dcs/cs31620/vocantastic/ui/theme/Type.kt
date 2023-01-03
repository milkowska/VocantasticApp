package uk.ac.aber.dcs.cs31620.vocantastic.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import uk.ac.aber.dcs.cs31620.vocantastic.R


val Railway = FontFamily(
    Font(R.font.raleway),
    )

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Railway,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)