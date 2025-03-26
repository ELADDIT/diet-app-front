package nutrinexus.ui.plans

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import nutrinexus.network.ApiService
import nutrinexus.network.WorkoutPlanResponse
import nutrinexus.ui.main.BaseScreen
import nutrinexus.viewmodel.AuthViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun WorkoutPlanScreen() {
    // Retrieve authentication data from AuthViewModel via Hilt
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUserId by authViewModel.currentUserId.collectAsState()
    val jwtToken by authViewModel.jwtToken.collectAsState()

    // State variables for API call
    var workoutPlans by remember { mutableStateOf<List<WorkoutPlanResponse>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Create Retrofit instance (for a real app, use Hilt to inject this)
    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5000/") // Adjust for your environment; for emulator, 10.0.2.2 = localhost
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java)

    // Make the API call when user data is available
    LaunchedEffect(currentUserId, jwtToken) {
        if (currentUserId == null || jwtToken.isNullOrEmpty()) {
            errorMessage = "User not authenticated"
            isLoading = false
            return@LaunchedEffect
        }
        try {
            workoutPlans = apiService.getWorkoutPlans(currentUserId!!, "Bearer $jwtToken")
        } catch (e: Exception) {
            errorMessage = e.message ?: "Error fetching workout plans"
        } finally {
            isLoading = false
        }
    }

    // Wrap the content in BaseScreen for a consistent UI
    BaseScreen(
        screenTitle = "Workout Plan",
        currentScreen = "Workouts", // This should match the identifier used in your bottom nav
        onNavItemClick = { destination ->
            // Handle navigation (e.g., using NavController) if needed
        }
    ) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Unknown error",
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            }
            workoutPlans != null -> {
                // For now, display the first workout plan
                val plan = workoutPlans!!.firstOrNull()
                if (plan != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Workout Plan ID: ${plan.workout_id}",
                            style = MaterialTheme.typography.h6,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Workout Details:",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.White
                        )
                        Text(
                            text = plan.workout_details ?: "No details provided",
                            style = MaterialTheme.typography.body1,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Notes:",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.White
                        )
                        Text(
                            text = plan.notes ?: "No additional notes",
                            style = MaterialTheme.typography.body2,
                            color = Color.White
                        )
                    }
                } else {
                    Text(
                        text = "No workout plan available",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
