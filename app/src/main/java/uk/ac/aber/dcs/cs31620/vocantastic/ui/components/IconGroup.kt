package uk.ac.aber.dcs.cs31620.vocantastic.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class IconGroup(
    val filledIcon: Painter,
    val outlinedIcon: Painter,
    val label: String
) {

}

