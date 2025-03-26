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
import nutrinexus.network.DietPlanResponse
import nutrinexus.ui.main.BaseScreen
import nutrinexus.viewmodel.AuthViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun DietPlanScreen() {
    // Obtain authentication info from AuthViewModel using Hilt
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUserId by authViewModel.currentUserId.collectAsState()
    val jwtToken by authViewModel.jwtToken.collectAsState()

    // State variables for the API call
    var dietPlans by remember { mutableStateOf<List<DietPlanResponse>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Create Retrofit instance (ideally this would be injected via Hilt)
    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5000/") // For emulator; adjust as needed
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
            dietPlans = apiService.getDietPlans(currentUserId!!, "Bearer $jwtToken")
        } catch (e: Exception) {
            errorMessage = e.message ?: "Error fetching diet plans"
        } finally {
            isLoading = false
        }
    }

    // Use BaseScreen to wrap our content for a consistent layout
    BaseScreen(
        screenTitle = "Diet Plan",
        currentScreen = "Diet",
        onNavItemClick = { destination ->
            // Handle navigation here (e.g., using NavController)
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
            dietPlans != null -> {
                val plan = dietPlans!!.firstOrNull()
                if (plan != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Diet Plan ID: ${plan.diet_id}",
                            style = MaterialTheme.typography.h6,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Meal Details:",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.White
                        )
                        Text(
                            text = plan.meal_details ?: "No details provided",
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
                        text = "No diet plan available",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
