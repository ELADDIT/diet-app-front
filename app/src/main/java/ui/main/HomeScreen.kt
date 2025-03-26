package nutrinexus.ui.main

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import nutrinexus.ui.main.BaseScreen

@Composable
fun HomeScreen() {
    BaseScreen(
        screenTitle = "Home",
        currentScreen = "Home",
        onNavItemClick = { destination ->
            // Handle navigation to different screens, e.g., using NavController
        }
    ) {
        // Place your HomeScreen-specific content here.
        // For now, we use a placeholder text.
        Text(
            text = "Home Screen Content",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground
        )
    }
}
