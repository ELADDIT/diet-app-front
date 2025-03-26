package nutrinexus.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

// Authentication models
data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val access_token: String, val user_id: Int, val role: String)
data class RegisterRequest(val username: String, val email: String, val password: String, val role: String = "client")
data class RegisterResponse(val msg: String, val user_id: Int)

// Appointment model
data class Appointment(
    val appointment_id: Int,
    val client_id: Int,
    val nutritionist_id: Int,
    val scheduled_at: String,
    val status: String
)

data class DietPlanResponse(
    val diet_id: Int,
    val start_date: String?,
    val end_date: String?,
    val meal_details: String?,
    val preferences_client_notes: String?,
    val notes: String?
)


data class WorkoutPlanResponse(
    val workout_id: Int,
    val start_date: String?,
    val end_date: String?,
    val workout_details: String?,
    val notes: String?
)


interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @GET("appointments")
    suspend fun getAppointments(
        @Header("Authorization") token: String
    ): List<Appointment>

    @GET("users/{user_id}/diet_plans")
    suspend fun getDietPlans(
        @Path("user_id") userId: Int,
        @Header("Authorization") token: String
    ): List<DietPlanResponse>

    @GET("users/{user_id}/workout_plans")
    suspend fun getWorkoutPlans(
        @Path("user_id") userId: Int,
        @Header("Authorization") token: String
    ): List<WorkoutPlanResponse>
}
