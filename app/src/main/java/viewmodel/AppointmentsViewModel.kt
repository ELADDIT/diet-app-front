package nutrinexus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nutrinexus.network.ApiService
import nutrinexus.network.Appointment
import javax.inject.Inject

@HiltViewModel
class AppointmentsViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Set this token after login (in a real app, share it via a repository)
    var jwtToken: String? = null

    fun fetchAppointments() {
        val token = jwtToken ?: return
        viewModelScope.launch {
            try {
                _appointments.value = apiService.getAppointments("Bearer $token")
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}
