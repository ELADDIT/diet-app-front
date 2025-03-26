package nutrinexus.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = DNAThemePrimary,
    secondary = DNAThemeSecondary,
    background = DNAThemeBackground
)

@Composable
fun NutriNexusTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
