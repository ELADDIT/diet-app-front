package nutrinexus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nutrinexus.network.ApiService
import nutrinexus.network.LoginRequest
import nutrinexus.network.RegisterRequest
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _jwtToken = MutableStateFlow<String?>(null)
    val jwtToken: StateFlow<String?> = _jwtToken

    // Add a currentUserId state
    private val _currentUserId = MutableStateFlow<Int?>(null)
    val currentUserId: StateFlow<Int?> = _currentUserId

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = apiService.login(LoginRequest(username, password))
                _jwtToken.value = response.access_token
                _currentUserId.value = response.user_id  // Set the current user id here
                _authError.value = null
            } catch (e: Exception) {
                _authError.value = "Login failed: ${e.message}"
            }
        }
    }

    fun register(username: String, email: String, password: String, role: String) {
        viewModelScope.launch {
            try {
                val response = apiService.register(RegisterRequest(username, email, password, role))
                // Optionally auto-login after registration
                login(username, password)
            } catch (e: Exception) {
                _authError.value = "Registration failed: ${e.message}"
            }
        }
    }
}
