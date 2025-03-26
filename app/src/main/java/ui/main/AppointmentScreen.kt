package nutrinexus.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nutrinexus.network.Appointment
import nutrinexus.viewmodel.AppointmentsViewModel

@Composable
fun AppointmentsScreen(viewModel: AppointmentsViewModel = hiltViewModel()) {
    viewModel.fetchAppointments()
    val appointments = viewModel.appointments.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value

    Scaffold(
        topBar = { TopAppBar(title = { Text("Appointments") }) }
    ) { paddingValues ->
        if (errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $errorMessage")
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(appointments) { appointment ->
                    AppointmentCard(appointment)
                }
            }
        }
    }
}

@Composable
fun AppointmentCard(appointment: Appointment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "ID: ${appointment.appointment_id}", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Scheduled: ${appointment.scheduled_at}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Status: ${appointment.status}")
        }
    }
}
