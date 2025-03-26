package nutrinexus.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nutrinexus.ui.theme.AppIcons
import nutrinexus.ui.theme.PageBackgroundColor
import nutrinexus.ui.theme.RectangleColor

@Composable
fun BaseScreen(
    screenTitle: String = "",
    currentScreen: String = "Home",
    onNavItemClick: (String) -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBackgroundColor)
    ) {
        // Decorative top-left rectangle
        Box(
            modifier = Modifier
                .offset(x = 0.dp, y = 0.dp)
                .size(100.dp)
                .clip(RoundedCornerShape(bottomEnd = 20.dp))
                .background(RectangleColor)
        )
        // Decorative top-right rectangle (adjust offset as needed)
        Box(
            modifier = Modifier
                .offset(x = 280.dp, y = 0.dp) // adjust based on screen size
                .size(100.dp)
                .clip(RoundedCornerShape(bottomStart = 20.dp))
                .background(RectangleColor)
        )
        // Main scaffold with common top and bottom bars
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = screenTitle) },
                    navigationIcon = {
                        IconButton(onClick = { /* Handle profile click */ }) {
                            Icon(
                                painter = AppIcons.User,
                                contentDescription = "Profile",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Handle mail click */ }) {
                            Icon(
                                painter = AppIcons.Mail,
                                contentDescription = "Messages",
                                tint = Color.White
                            )
                        }
                    },
                    backgroundColor = RectangleColor,
                    contentColor = Color.White,
                    elevation = 4.dp
                )
            },
            bottomBar = {
                BottomNavigation(backgroundColor = RectangleColor, contentColor = Color.White) {
                    BottomNavigationItem(
                        icon = { Icon(painter = AppIcons.Meal, contentDescription = "Diet", tint = Color.White) },
                        label = { Text("Diet") },
                        selected = currentScreen == "diet_plan",
                        onClick = { onNavItemClick("diet_plan") }
                    )
                    BottomNavigationItem(
                        icon = { Icon(painter = AppIcons.Chart, contentDescription = "Stats", tint = Color.White) },
                        label = { Text("Stats") },
                        selected = currentScreen == "Stats",
                        onClick = { onNavItemClick("Stats") }
                    )
                    BottomNavigationItem(
                        icon = { Icon(painter = AppIcons.Home, contentDescription = "Home", tint = Color.White) },
                        label = { Text("Home") },
                        selected = currentScreen == "home",
                        onClick = { onNavItemClick("home") }
                    )
                    BottomNavigationItem(
                        icon = { Icon(painter = AppIcons.Calendar, contentDescription = "Appointments", tint = Color.White) },
                        label = { Text("Calendar") },
                        selected = currentScreen == "Calendar",
                        onClick = { onNavItemClick("Calendar") }
                    )
                    BottomNavigationItem(
                        icon = { Icon(painter = AppIcons.Weights, contentDescription = "Workouts", tint = Color.White) },
                        label = { Text("Workout") },
                        selected = currentScreen == "workout_plan",
                        onClick = { onNavItemClick("workout_plan") }
                    )
                }
            },
            backgroundColor = Color.Transparent
        ) { innerPadding ->
            // Content area for the specific screen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
        // Decorative bottom rectangle (optional)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(RectangleColor)
        )
    }
}
